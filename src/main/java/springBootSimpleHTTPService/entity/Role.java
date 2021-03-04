package springBootSimpleHTTPService.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase de la capa de dominio.
 *
 * La anotacion @Entity indica que la clase es una entidad.
 *
 * La anotacion @Table indica que la clase sera mapeada a una tabla y persistida, en este caso,
 * a una base de datos embebida de tipo H2.
 */
@Entity
@Table(name="role") //en caso que la tabla sea diferente
public class Role implements Serializable {

    //Atributos de entidad salary
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //busca ultimo valor e incrementa desde id final de db
    private Long id;

    @Column(name = "jobtitle")//no hace falta si se llama igual
    private JobEnum jobEnum;
    @Column(name = "annualsalary")
    private BigDecimal annualSalary;

    public Role() {
    }

    public Role(Long id, JobEnum jobEnum, BigDecimal annualSalary) {
        this.id = id;
        this.jobEnum = jobEnum;
        this.annualSalary = annualSalary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobEnum getJobEnum() {
        return jobEnum;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", job='" + jobEnum.getJobTitle() + '\'' +
                ", annualSalary=" + annualSalary +
                '}';
    }
}
