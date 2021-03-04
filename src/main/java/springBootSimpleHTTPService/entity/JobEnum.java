package springBootSimpleHTTPService.entity;

import java.math.BigDecimal;

/**
 * Tipo Enum de la capa de dominio.
 *
 * Define los empleos disponibles en el sistema que son fijos
 * Cada elemento del Enum esta formado por los campos:
 * 'jobTitle', String para indicar el nombre del empleo
 * 'dollars', BigDecimal para indicar el salario que corresponde a cada empleo
 *
 */
public enum JobEnum {

    Software_Engineer("Software Engineer", BigDecimal.valueOf(45000)),
    Data_Scientist("Data Scientist", BigDecimal.valueOf(47000)),
    QA_Tester("QA Tester", BigDecimal.valueOf(30000)),
    Support_Specialist("Support Specialist", BigDecimal.valueOf(35000)),
    Web_Developer("Web Developer", BigDecimal.valueOf(34000)),
    Web_Administrator("Web Administrator", BigDecimal.valueOf(32000)),
    IT_Technician("IT Technician", BigDecimal.valueOf(36000)),
    UX_Designer("UX Designer", BigDecimal.valueOf(31000)),
    Database_Administrator("Database Administrator", BigDecimal.valueOf(40000)),
    IT_Director("IT Director", BigDecimal.valueOf(67000)),
    Cloud_System_Engineer("Cloud System Engineer", BigDecimal.valueOf(55000));

    private String jobTitle;
    private BigDecimal dollars;

    JobEnum(String jobTitle, BigDecimal dollars) {
        this.jobTitle = jobTitle;
        this.dollars = dollars;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getDollars() {
        return dollars;
    }

    public void setDollars(BigDecimal dollars) {
        this.dollars = dollars;
    }
}
