package org.miracum.streams.ume.onkoadttofhir.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ADT_GEKID implements Serializable {

  @JsonProperty private String Schema_Version;

  @JsonProperty private Absender Absender;

  @JacksonXmlElementWrapper(useWrapping = false)
  private List<Menge_Patient> Menge_Patient;

  @Data
  public static class Absender {
    @JsonProperty private String Absender_Bezeichnung;

    @JsonProperty private String Absender_Ansprechpartner;

    @JsonProperty private String Absender_Anschrift;
  }

  @Data
  public static class Menge_Patient {

    @JsonProperty private Patient Patient;

    @Data
    public static class Patient {

      @JsonProperty private Patienten_Stammdaten Patienten_Stammdaten;
      @JsonProperty private Menge_Meldung Menge_Meldung; // TODO Liste an Meldungen?

      @Data
      public static class Patienten_Stammdaten {

        @JsonProperty private String KrankenversichertenNr;
      }

      @Data
      public static class Menge_Meldung {

        @JsonProperty private Meldung Meldung;

        @Data
        public static class Meldung {
          @JsonProperty private String Meldung_ID;

          @JsonProperty private String Meldedatum;
          @JsonProperty private Diagnose Diagnose;
          // TODO - brauchen wir vll auch die Tumorzuordnung? z.B. bei Behandlungsende für die
          // Dokumentation einer OP können wir nur so zuordnen, falls mehrere Tumore
          @JsonProperty private Menge_OP Menge_OP;

          @Data
          public static class Diagnose {

            @JsonProperty private String Tumor_ID;
            @JsonProperty private String Primaertumor_ICD_Code;
            @JsonProperty private String Primaertumor_ICD_Version;
            // @JsonProperty private String ICD_O_Topographie_Code;  nicht sicher ob notwendig, aber
            // wenn's schon da ist, why not
            // @JsonProperty private String ICD_O_Topographie_Version;
            @JsonProperty private String Diagnosedatum;
            @JsonProperty private String Seitenlokalisation;

            @JsonProperty
            private Menge_Histologie
                Menge_Histologie; // TODO Achtung! Manchmal ist Histologie + pTNM in Menge_OP; es
            // könnte mehrere Histologien geben bei mehrere Tumoren

            @JsonProperty private cTNM cTNM;
            @JsonProperty private pTNM pTNM;

            @Data
            public static class Menge_Histologie {
              @JsonProperty private Histologie Histologie;

              @Data
              public static class Histologie {

                @JsonProperty private String Histologie_ID;
                @JsonProperty private String Tumor_Histologiedatum;
                @JsonProperty private String Morphologie_Code; // Morphologie_ICD_O_3_Code
                @JsonProperty private String Morphologie_ICD_O_Version; //
                @JsonProperty private String Grading;
                @JsonProperty private String Morphologie_Freitext;
              }
            }

            @Data
            public static class cTNM {
              @JsonProperty private String cTNM_Datum;
              @JsonProperty private String cTNM_Version;
              // @JsonProperty private String cTNM_T_praefix;    // TODO ich glaube die Praefixe
              // brauchen wir nur bei OP
              @JsonProperty private String cTNM_T;
              // @JsonProperty private String cTNM_N_praefix;
              @JsonProperty private String cTNM_N;
              // @JsonProperty private String cTNM_M_praefix;
              @JsonProperty private String cTNM_M;
            }

            @Data
            public static class pTNM {
              @JsonProperty private String pTNM_Datum;
              @JsonProperty private String pTNM_Version;
              // @JsonProperty private String pTNM_T_praefix;
              @JsonProperty private String pTNM_T;
              // @JsonProperty private String pTNM_N_praefix;
              @JsonProperty
              private String
                  pTNM_N; // TODO kommt auch vor, dass zb nur dieser Wert gefüllt wird nach
              // patholog. Befund (ohne T, M)
              // @JsonProperty private String pTNM_M_praefix;
              @JsonProperty private String pTNM_M;
            }
          } // Diagnose Ende

          @Data
          public static class Menge_OP {
            @JsonProperty private OP OP;

            @Data
            public static class OP {
              @JsonProperty
              private OP_Histologie OP_Histologie; // TODO müssen genau so heißen wie XML TAGS

              @JsonProperty private OP_TNM OP_TNM;

              @Data
              public static class OP_Histologie {
                @JsonProperty private String OP_Histologie_ID;
                @JsonProperty private String OP_Histologie_Datum;
                @JsonProperty private String OP_Morphologie_ICD_O_3_Code;
                @JsonProperty private String OP_ICD_O_3_Version;
                @JsonProperty private String OP_Grading;
              }

              @Data
              public static class OP_TNM {
                @JsonProperty private String OP_TNM_Datum;
                @JsonProperty private String OP_TNM_Version;

                @JsonProperty
                private String OP_TNM_T_praefix; // hier ist c oder p möglich (Abfrage im Mapper)

                @JsonProperty private String OP_TNM_T;
                @JsonProperty private String OP_TNM_N_praefix;
                @JsonProperty private String OP_TNM_N;
                @JsonProperty private String OP_TNM_M_praefix;
                @JsonProperty private String OP_TNM_M;
              }
            }
          }
        }
      }
    }
  }
}
