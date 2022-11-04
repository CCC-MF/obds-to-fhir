package org.miracum.streams.ume.onkoadttofhir;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fhir")
@Data
public class FhirProperties {
  private FhirSystems systems;
  private FhirDisplay display;
  private FhirProfiles profiles;
  private FhirUrl url;

  @Data
  public static class FhirSystems {
    private String patientId;
    private String identifierType;
    private String conditionId;
    private String ObservationId;
    private String observationCategorySystem;
    private String loinc;
    private String idco3Morphologie;
    private String gradingDktk;
    private String uicc;
    private String tnmPraefix;
    private String tnmTCs;
    private String tnmNCs;
    private String tnmMCs;
    private String tnmYSymbolCs;
    private String tnmRSymbolCs;
    private String tnmMSymbolCs;
  }

  @Data
  public static class FhirProfiles {
    private String histologie;
    private String grading;
    private String tnmC;
    private String tnmP;
  }

  @Data
  public static class FhirDisplay {
    private String histologyLoinc;
    private String gradingLoinc;
    private String tnmcLoinc;
  }

  @Data
  public static class FhirUrl {
    private String tnmPraefix;
  }
}
