package springBootSimpleHTTPService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import springBootSimpleHTTPService.controller.EmployeeController;
import springBootSimpleHTTPService.dto.EmployeeDto;
import springBootSimpleHTTPService.entity.Employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Clase de la capa Controller
 *
 * Implemente la interfaz RepresentationModelAssembler
 * (pertenece al modulo de Spring HATEOAS, org.springframework.hateoas.server)
 *
 * Convierte un objeto de dominio en un RepresentationModel, que es una clase base
 * para que los DTO recopilen enlaces, un EntityModel simple que envuelve un objeto
 * de dominio y le agrega enlaces.
 *
 * Anotaciones:
 *
 * @Component
 * Indica que una clase es un "componente".
 * Estas clases se consideran candidatas para la detección automática cuando se utiliza una configuración
 * basada en anotaciones y un escaneo de classpath.
 * También se pueden considerar otras anotaciones a nivel de clase como identificación de un componente,
 * normalmente un tipo especial de componente: por ejemplo, la anotación @Repository
 *
 */
@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<EmployeeDto>> {

    @Autowired
    private EmployeeModelMapperConvert employeeModelMapperConvert;

    private String jsonSchema = "{\"firstName\":\"value1\", \"lastName\":\"value2\", \"role\": {\"id\": [1-11]}}";

    /**
     * Metodo abstracto de la interfaz RepresentationModelAssembler
     * Convierte un objeto de tipo Employee en un EntityModel de tipos EmployeeDto.
     * El objeto EntityModel envuelve un objeto de dominio y le agrega enlaces
     *
     * @param employee, objeto de tipo Employee
     * @return objeto de tipo EntityModel que envuelve a un objeto de tipo EmployeeDto
     * y le agrega enlaces
     */
    public EntityModel<EmployeeDto> toModel(Employee employee) {

        EmployeeDto employeeDto = employeeModelMapperConvert.convertToDto(employee);

        return EntityModel.of(employeeDto,
                WebMvcLinkBuilder.linkTo(methodOn(EmployeeController.class).oneEmployee(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).allEmployees()).withRel("all"),
                linkTo(methodOn(EmployeeController.class).newEmployee(employee)).withRel(
                "curl -X POST -d '"+ jsonSchema + "'" +
                        " -H \"Content-Type: application/json\" "),
                linkTo(methodOn(EmployeeController.class).updateEmployee(employee, employee.getId())).withRel(
                        "curl -X PUT -d '"+ jsonSchema + "'" +
                                " -H \"Content-Type: application/json\" "),
                linkTo(methodOn(EmployeeController.class).deleteEmployee(employee.getId())).withRel("delete"),
                linkTo(methodOn(EmployeeController.class).findEmployeesByRoleId(employee.getRole().getId())).withRel("getByRoleId [1-11]"),
                linkTo(methodOn(EmployeeController.class).findEmployeesByRolePathVariable(employee.getRole().getJobEnum().getJobTitle())).withRel("getByRolePathVariable"),
                linkTo(methodOn(EmployeeController.class).findEmployeesByRoleRequestParam(employee.getRole().getJobEnum().getJobTitle())).withRel("getByRoleRequestParam")
                );

    }

    public String getJsonSchema() {
        return jsonSchema;
    }

}
