package org.miracum.streams.ume.obdstofhir.mapper.mii;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import ca.uhn.fhir.context.FhirContext;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule;
import de.basisdatensatz.obds.v3.OBDS;
import java.io.IOException;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.miracum.streams.ume.obdstofhir.FhirProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {FhirProperties.class})
@EnableConfigurationProperties
class PatientMapperTest {
  private final PatientMapper sut;

  @Autowired
  PatientMapperTest(FhirProperties fhirProps) {
    sut = new PatientMapper(fhirProps);
  }

  @Test
  void map_withGivenObds_shouldCreateValidPatientResource() throws IOException {
    // TODO: refactor to use a data provider for parameterized tests
    final var resource = this.getClass().getClassLoader().getResource("obds3/test1.xml");
    assertThat(resource).isNotNull();

    final var xmlMapper =
        XmlMapper.builder()
            .defaultUseWrapper(false)
            .addModule(new JakartaXmlBindAnnotationModule())
            .addModule(new Jdk8Module())
            .build();

    final var obds = xmlMapper.readValue(resource.openStream(), OBDS.class);

    var obdsPatient = obds.getMengePatient().getPatient().getFirst();

    final var patient =
        sut.map(obdsPatient.getPatientenStammdaten(), obdsPatient.getMengeMeldung().getMeldung());

    var fhirParser = FhirContext.forR4().newJsonParser().setPrettyPrint(true);
    var fhirJson = fhirParser.encodeResourceToString(patient);
    Approvals.verify(fhirJson, new Options().forFile().withExtension(".fhir.json"));
  }
}