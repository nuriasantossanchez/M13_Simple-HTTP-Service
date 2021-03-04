package springBootSimpleHTTPService.dto;

import java.math.BigDecimal;

/**
 * Clase de la capa de dominio, implementa el patron Data Transfer Object (DTO Pattern) mediante la
 * creacion de un objeto plano (POJO) con una serie de atributos que puedan ser enviados o recuperados
 * del servidor en una sola invocación (de tal forma que un DTO puede contener información de multiples
 * fuentes o tablas y concentrarlas en una unica clase simple, esto es, crear estructuras de datos
 * independientes del modelo de datos, para transmitir información entre un cliente y un servidor)
 *
 * Anotaciones:
 * @Component
 * Indica que una clase es un "componente".
 * Estas clases se consideran candidatas para la deteccion automatica cuando se utiliza una configuración
 * basada en anotaciones y un escaneo de classpath.
 * También se pueden considerar otras anotaciones a nivel de clase como identificación de un componente,
 * normalmente un tipo especial de componente: por ejemplo, la anotación @Repository
 */
public class RoleDto extends ResponseDto{

    private Long id;
    private String jobTitle;
    private BigDecimal annualSalary;

    public RoleDto() {
    }

    public RoleDto(Long id, String jobTitle, BigDecimal annualSalary) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.annualSalary = annualSalary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(BigDecimal annualSalary) {
        this.annualSalary = annualSalary;
    }
}
