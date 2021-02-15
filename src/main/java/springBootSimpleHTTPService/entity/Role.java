package springBootSimpleHTTPService.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

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

  /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
   private List<Employee> employees;

   */

    public Role() {
    }

    public Role(Long id, JobEnum jobEnum, BigDecimal annualSalary) {
        this.id = id;
        this.jobEnum = jobEnum;
        this.annualSalary = annualSalary;
    }

    /*
    public Role(Long id, JobEnum jobEnum, List<Employee> employees) {
        this.id = id;
        this.jobEnum = jobEnum;
        this.annualSalary = jobEnum.getDollars();
        this.employees = employees;
    }

     */

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
