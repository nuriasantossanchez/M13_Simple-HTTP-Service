package springBootSimpleHTTPService.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Clase de la capa de dominio.
 *
 * La anotacion @Entity indica que la clase es una entidad.
 *
 * La anotacion @Table indica que la clase sera mapeada a una tabla y persistida, en este caso,
 * a una base de datos embebida de tipo H2.
 *
 * La anotacion @ManyToOne especifica una asociación de un solo valor a otra clase de entidad
 * que tiene multiplicidad de muchos a uno, en este caso, la asociacion es con la entidad Role,
 * donde un Employee tiene un unico Role y un Role puede estar asociado a multiples Employees
 */
@Entity
@Table(name="employee") //en caso que la tabla sea diferente
public class Employee implements Serializable {

    //Atributos de entidad employee
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//busca ultimo valor e incrementa desde id final de db
    private Long id;

    @Column(name = "firstname")//no hace falta si se llama igual
    @NotEmpty(message = "\"firstName\" with a String value is required")
    private String firstName;

    @Column(name = "lastname")//no hace falta si se llama igual
    @NotEmpty(message = "\"lastName\" with a String value is required")
    private String lastName;

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    @NotNull(message = "\"role\" with a {\"id\"} element is required")
    private Role role;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastname, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastname;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                '}';
    }

}
