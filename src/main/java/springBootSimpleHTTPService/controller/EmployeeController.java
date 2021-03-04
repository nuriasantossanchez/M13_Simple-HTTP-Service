package springBootSimpleHTTPService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springBootSimpleHTTPService.controller.exception.EmployeeNotFoundException;
import springBootSimpleHTTPService.controller.exception.RoleNotFoundException;
import springBootSimpleHTTPService.dto.EmployeeDto;
import springBootSimpleHTTPService.entity.Employee;
import springBootSimpleHTTPService.entity.Role;
import springBootSimpleHTTPService.service.IEmployeeService;
import springBootSimpleHTTPService.service.IRoleService;
import springBootSimpleHTTPService.util.EmployeeModelAssembler;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * Clase de la capa Controller.
 * La anotacion @RestController convierte a la aplicacion en un REST Service, basado en el intercambio
 * de recursos (elementos de informacion) entre componentes de la red, clientes y servidores, que
 * se comunican a traves del protocolo HTTP
 *
 * Anotaciones:
 *
 * @RestController
 * Anotacion que a su vez esta anotada con @Controller y @ResponseBody.
 * Los tipos que llevan esta anotacion se tratan como controladores donde los metodos @RequestMapping
 * asumen la semantica @ResponseBody por defecto
 *
 * @Autowired
 * Marca un constructor, campo, metodo setter o metodo de configuracion para ser detectado
 * automaticamente por la funcionalidad de inyeccion de dependencias de Spring
 *
 * @GetMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.GET).
 * El punto de acceso a la peticion sera http://localhost:8181/{path}, en este caso
 * (el puerto 8181 queda especificado en el archivo application.properties, del directorio resources)
 *
 * @PostMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.POST).
 *
 * @PutMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.PUT).
 *
 * @DeleteMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.DELETE).
 */
@RestController
public class EmployeeController {

    private final IEmployeeService iEmployeeService;
    private final IRoleService iRoleService;
    private final EmployeeModelAssembler employeeModelAssembler;

    /**
     * Constructor de la clase, parametrizado con las interfaces IEmployeeService, IRoleService y la clase
     * RoleModelAssembler, que implementa la interface RepresentationModelAssembler
     * Marcado con la anotacion @Autowired, la clase controlador es automaticamente detectada por Spring
     *
     * @param iEmployeeService, interfaz de tipo IEmployeeService, implementada por la clase EmployeeServiceImpl,
     *                          en la que se exponen los servicios o funcionalidades accesibles via HTTP
     * @param iRoleService, interfaz de tipo IRoleService, implementada por la clase RoleServiceImpl,
     *                      en la que se exponen los servicios o funcionalidades accesibles via HTTP
     * @param employeeModelAssembler, instancia de tipo EmployeeModelAssembler, convierte un objeto de dominio en un
     *                            RepresentationModel, esto es, un EntityModel que envuelve al objeto de dominio
     *                            y lo agrega enlaces
     */
    @Autowired
    public EmployeeController(IEmployeeService iEmployeeService, IRoleService iRoleService,
                              EmployeeModelAssembler employeeModelAssembler) {
        this.iEmployeeService = iEmployeeService;
        this.iRoleService = iRoleService;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL http://localhost:8181/employees
     *
     * Accede a la capa de servicio EmployeeServiceImpl mediante su interface IEmployeeService
     * y hace uso del metodo 'listEmployees()' para recuperar un listado de tipos EmployeeDto
     * en forma de ResponseEntity, esto es, agregando enlaces al objeto de dominio
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de tipos EmployeeDto,
     * que contiene todos los empleados disponibles en el sistema, junto con enlaces agregados
     */
    @GetMapping("/employees")
    public ResponseEntity<?> allEmployees(){

        List<EntityModel<EmployeeDto>> employees = iEmployeeService.listEmployees().stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<EmployeeDto>> collectionModel =
                CollectionModel.of(employees,
                        linkTo(methodOn(EmployeeController.class).allEmployees()).withSelfRel());

        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);

    }

    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL http://localhost:8181/employees/{valor numerico}
     *
     * Accede a la capa de servicio EmployeeServiceImpl mediante su interface IEmployeeService
     * y hace uso del metodo 'findEmployeeById(id)' para recuperar un objeto de tipo EmployeeDto
     * en forma de ResponseEntity, esto es, agregando enlaces al objeto de dominio
     *
     * @param id, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *            y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *            Indica el id del empleado que se quiere obtener
     *
     * @return objeto generico de tipo ResponseEntity, formado por un objeto de tipo EmployeeDto,
     * que contiene un empleado concreto del sistema, determinado por el valor numerico de su id,
     * junto con enlaces agregados
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<?> oneEmployee(@PathVariable(name="id") Long id) {

        Employee employeeEntity = iEmployeeService.findEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        Optional<Role> role = iRoleService.findRoleById(employeeEntity.getRole().getId());

        employeeEntity.setRole(role.get());

        EntityModel<EmployeeDto> entityModel = employeeModelAssembler.toModel(employeeEntity);

        System.out.println("Employee By Id: \n"+employeeEntity);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /**
     * Representa el mapeo de una peticion HTTP POST, a la URL http://localhost:8181/employees
     *
     * Accede a la capa de servicio EmployeeServiceImpl mediante su interface IEmployeeService
     * y hace uso del metodo 'saveEmployee(employee)' para salvar el nuevo objeto creado,
     * de tipo EmployeeDto, en forma de ResponseEntity, esto es, agregando enlaces al objeto de dominio
     *
     * @param newEmployee, tipo Employee anotado con @RequestBody para indicar que el parametro de metodo
     *                     debe estar vinculada al cuerpo de la solicitud web.
     *                     El cuerpo de la solicitud se pasa en formato JSON, segun el tipo de contenido de la solicitud.
     *                     Se aplica la validacion automatica anotando el argumento con @Valid
     *
     * @return objeto generico de tipo ResponseEntity, formado por un objeto de tipo EmployeeDto,
     * que contiene el nuevo empleado creado, junto con enlaces agregados
     */
    @PostMapping("/employees")
    public ResponseEntity<?> newEmployee(@Valid @RequestBody Employee newEmployee) {

        if (null != newEmployee.getRole().getId()) {

            newEmployee.setRole(iRoleService.findRoleById(newEmployee.getRole().getId()).get());

            EntityModel<EmployeeDto> entityModel = employeeModelAssembler.toModel(iEmployeeService.saveEmployee(newEmployee));

            System.out.println("New Employee: \n" + newEmployee.toString());

            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Bad Request. Please provide a valid JSON value")
                        .withDetail(employeeModelAssembler.getJsonSchema()));

    }

    /**
     * Representa el mapeo de una peticion HTTP PUT, a la URL http://localhost:8181/employees/{valor numerico}
     *
     * Accede a la capa de servicio EmployeeServiceImpl mediante su interface IEmployeeService
     * y hace uso del metodo 'saveEmployee(employee)' para salvar el objeto modificado o crear uno nuevo,
     * en caso de que no exista en el sistema un empleado con un id que corresponda al valor numerico
     * pasado en la URL
     *
     * @param newEmployee, tipo Employee anotado con @RequestBody para indicar que el parametro de metodo
     *                     debe estar vinculada al cuerpo de la solicitud web.
     *                     El cuerpo de la solicitud se pasa en formato JSON, segun el tipo de contenido de la solicitud.
     *                     Se aplica la validacion automatica anotando el argumento con @Valid
     *
     * @param id, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *            y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *            Indica el id del empleado a modificar. En caso de que no exista un empleado
     *            con ese id, crea uno nuevo
     *
     * @return objeto generico de tipo ResponseEntity, formado por un objeto de tipo EmployeeDto,
     * que contiene el empleado modificado (o nuevo), junto con enlaces agregados
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody Employee newEmployee, @PathVariable(name="id") Long id) {

        if (null != newEmployee.getRole().getId()) {
            Employee updatedEmployee = iEmployeeService.findEmployeeById(id)
                    .map(employee -> {
                        employee.setFirstName(newEmployee.getFirstName());
                        employee.setLastName(newEmployee.getLastName());
                        employee.setRole(iRoleService.findRoleById(newEmployee.getRole().getId()).get());
                        return iEmployeeService.saveEmployee(employee);
                    })
                    .orElseGet(() -> {
                        newEmployee.setId(id);
                        return iEmployeeService.saveEmployee(newEmployee);
                    });

            EntityModel<EmployeeDto> entityModel = employeeModelAssembler.toModel(updatedEmployee);

            System.out.println("El empleado actualizado es: \n" + updatedEmployee);

            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Bad Request. Please provide a valid JSON value")
                        .withDetail(employeeModelAssembler.getJsonSchema()));

    }

    /**
     * Representa el mapeo de una peticion HTTP DELETE, a la URL http://localhost:8181/employees/{valor numerico}
     *
     * Accede a la capa de servicio EmployeeServiceImpl mediante su interface IEmployeeService
     * y hace uso de los metodos 'findEmployeeById(id)' para recuperar el objeto de tipo EmployeeDto,
     * y deleteEmployee(id), para eliminar el empleado determinado por el valor numerico pasado en la URL
     *
     * @param id, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *            y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *            Indica el id del empleado que se quiere eliminar
     *
     * @return objeto generico de tipo ResponseEntity, con una respuesta de operacion valida
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name="id")Long id) {

        Employee deletedEmployee = iEmployeeService.findEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        System.out.println("El empleado eliminado es: \n"+ deletedEmployee);

        iEmployeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL http://localhost:8181/employees/roleid/{valor numerico [1-11]}
     *
     * Accede a la capa de servicio RoleServiceImpl mediante su interface IRoleService
     * y hace uso del metodo 'findRoleById(roleId)' para recuperar un listado de objetos
     * de tipo EmployeeDto cuyo empleo se corresponda al id del empleo pasado en la URL
     *
     * @param roleId, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *            y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *            Indica el id del role/empleo que se utiliza para hacer la busqueda de empleados por role
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de objetos de tipo EmployeeDto,
     * que contiene todos los empleados que desempeñan un trabajo determinado, junto con enlaces agregados
     */
    @GetMapping("/employees/roleid/{role_id}")
    public ResponseEntity<?> findEmployeesByRoleId(@PathVariable(name="role_id") Long roleId) {

        Optional<Role> role = iRoleService.findRoleById(roleId);

        System.out.println("El role by ID es: \n"+ role);

        List<EntityModel<EmployeeDto>> employees = getEmployeesByRoleId(role.get().getId());

        CollectionModel<EntityModel<EmployeeDto>> collectionModel = CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).findEmployeesByRoleId(role.get().getId())).withSelfRel());


        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);

    }
    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL http://localhost:8181/employees/role/{valor String}
     *
     * Accede a la capa de servicio RoleServiceImpl mediante su interface IRoleService
     * y hace uso del metodo 'findRoleByJobTitle(jobTitle)' para recuperar el objeto Role.
     * Despues llama al metodo getEmployeesByRoleId(roleId), donde accede a la capa de servicio
     * EmployeeServiceImpl mediante su interface IEmployeeService y hace uso del metodo
     * findEmployeesByRoleId(roleId) para recuperar un listado de objetos de tipo EmployeeDto
     * cuyo empleo se corresponda al role pasado en la URL.
     *
     * @param jobTitle, tipo String anotado con @PathVariable para indicar que es un parametro de metodo
     *            y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *            Indica el nombre del role/empleo que se utiliza para hacer la busqueda de empleados por role
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de objetos de tipo EmployeeDto,
     * que contiene todos los empleados que desempeñan un trabajo determinado, junto con enlaces agregados
     */
    @GetMapping("/employees/role/{job}")
    public ResponseEntity<?> findEmployeesByRolePathVariable(@PathVariable(name="job") String jobTitle) {

        Optional<Role> role = Optional.ofNullable(iRoleService.findRoleByJobTitle(jobTitle)
                .orElseThrow(() -> new RoleNotFoundException(jobTitle)));

        System.out.println("[PathVariable] El role by jobTitle es: \n"+ role);

        List<EntityModel<EmployeeDto>> employees = getEmployeesByRoleId(role.get().getId());

        CollectionModel<EntityModel<EmployeeDto>> collectionModel = CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).findEmployeesByRolePathVariable(jobTitle)).withSelfRel());

        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);
    }

    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL http://localhost:8181/employees/role
     *
     * Accede a la capa de servicio RoleServiceImpl mediante su interface IRoleService
     * y hace uso del metodo 'findRoleByJobTitle(jobTitle)' para recuperar el objeto Role.
     * Despues llama al metodo getEmployeesByRoleId(roleId), donde accede a la capa de servicio
     * EmployeeServiceImpl mediante su interface IEmployeeService y hace uso del metodo
     * findEmployeesByRoleId(roleId) para recuperar un listado de objetos de tipo EmployeeDto
     * cuyo empleo se corresponda al role pasado como parametro de la solicitud web.
     *
     * @param jobTitle, tipo String anotado con @RequestParam para indicar que es un parametro de metodo
     *            y debe estar vinculado a un parametro de solicitud web, en este caso parametro "job".
     *            Indica el nombre del role/empleo que se utiliza para hacer la busqueda de empleados por role
     *
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de objetos de tipo EmployeeDto,
     * que contiene todos los empleados que desempeñan un trabajo determinado, junto con enlaces agregados
     */
    @GetMapping("/employees/role")
    public ResponseEntity<?> findEmployeesByRoleRequestParam(@RequestParam(name="job") String jobTitle) {

        Optional<Role> role = Optional.ofNullable(iRoleService.findRoleByJobTitle(jobTitle)
                .orElseThrow(() -> new RoleNotFoundException(jobTitle)));

        System.out.println("[RequestParam] El role by jobTitle es: \n"+ role);

        List<EntityModel<EmployeeDto>> employees = getEmployeesByRoleId(role.get().getId());

        CollectionModel<EntityModel<EmployeeDto>> collectionModel = CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).findEmployeesByRoleRequestParam(jobTitle)).withSelfRel());

        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);

    }

    /**
     * Accede a la capa de servicio EmployeeServiceImpl mediante su interface IEmployeeService y hace uso
     * del metodo findEmployeesByRoleId(roleId) para recuperar un listado de objetos de tipo EmployeeDto
     * cuyo empleo/role tenga el mismo id que el valor pasado como parametro
     *
     * @param id, tipo Long, indica el id del role/empleo que se utiliza para recuperar los empleados
     *            cuyo role_id se corresponde con dicho valor
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de objetos de tipo EmployeeDto,
     * que contiene todos los empleados que desempeñan un trabajo determinado, junto con enlaces agregados
     */
    private List<EntityModel<EmployeeDto>> getEmployeesByRoleId(Long id) {
        return iEmployeeService.findEmployeesByRoleId(id).stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());
    }

}
