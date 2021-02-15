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

@RestController
public class EmployeeController {

    private final IEmployeeService iEmployeeService;
    private final IRoleService iRoleService;
    private final EmployeeModelAssembler employeeModelAssembler;

    @Autowired
    public EmployeeController(IEmployeeService iEmployeeService, IRoleService iRoleService,
                              EmployeeModelAssembler employeeModelAssembler) {
        this.iEmployeeService = iEmployeeService;
        this.iRoleService = iRoleService;
        this.employeeModelAssembler = employeeModelAssembler;
    }

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

    @PostMapping("/employees")
    public ResponseEntity<?> newEmployee(@Valid @RequestBody Employee newEmployee) {

        if (null != newEmployee.getRole().getId()) {
            Optional<Role> role = Optional.ofNullable(iRoleService.findRoleById(newEmployee.getRole().getId())
                    .orElseThrow(() -> new RoleNotFoundException(newEmployee.getRole().getId())));

            newEmployee.setRole(role.get());

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
                        .withDetail(employeeModelAssembler.getJsonValue()));

    }


    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody Employee newEmployee, @PathVariable(name="id") Long id) {

        if (null != newEmployee.getRole().getId()) {
            Employee updatedEmployee = iEmployeeService.findEmployeeById(id)
                    .map(employee -> {
                        employee.setFirstName(newEmployee.getFirstName());
                        employee.setLastName(newEmployee.getLastName());
                        Optional<Role> role = Optional.ofNullable(iRoleService.findRoleById(newEmployee.getRole().getId())
                                .orElseThrow(() -> new RoleNotFoundException(newEmployee.getRole().getId())));
                        employee.setRole(role.get());
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
                        .withDetail(employeeModelAssembler.getJsonValue()));

    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name="id")Long id) {

        //Optional<Employee> deletedEmployee= iEmployeeService.findEmployeeById(id);

        Employee deletedEmployee = iEmployeeService.findEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        System.out.println("El empleado eliminado es: \n"+ deletedEmployee);

        iEmployeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/employees/roleid/{role_id}")
    public ResponseEntity<?> findEmployeesByRoleId(@PathVariable(name="role_id") Long roleId) {

        Optional<Role> role = iRoleService.findRoleById(roleId);

        List<EntityModel<EmployeeDto>> employees = getEmployeesByRoleId(role.get().getId());

        CollectionModel<EntityModel<EmployeeDto>> collectionModel = CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).findEmployeesByRoleId(role.get().getId())).withSelfRel());


        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);

    }

    @GetMapping("/employees/role/{job}")
    public ResponseEntity<?> findEmployeesByRolePathVariable(@PathVariable(name="job") String jobTitle) {

        iRoleService.findRoleByJobTitle(jobTitle);

        Optional<Role> role = iRoleService.findRoleByJobTitle(jobTitle);

        System.out.println("El role es: \n"+ role);

        List<EntityModel<EmployeeDto>> employees = getEmployeesByRoleId(role.get().getId());

        CollectionModel<EntityModel<EmployeeDto>> collectionModel = CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).findEmployeesByRolePathVariable(jobTitle)).withSelfRel());

        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);
    }

    @GetMapping("/employees/role")
    public ResponseEntity<?> findEmployeesByRoleRequestParam(@RequestParam(name="job") String jobTitle) {

        Optional.ofNullable(iRoleService.findRoleByJobTitle(jobTitle)
                .orElseThrow(() -> new RoleNotFoundException(jobTitle)));

        Optional<Role> role = iRoleService.findRoleByJobTitle(jobTitle);

        System.out.println("El role es: \n"+ role);

        List<EntityModel<EmployeeDto>> employees = getEmployeesByRoleId(role.get().getId());

        CollectionModel<EntityModel<EmployeeDto>> collectionModel = CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).findEmployeesByRoleRequestParam(jobTitle)).withSelfRel());

        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);

    }

    private List<EntityModel<EmployeeDto>> getEmployeesByRoleId(Long id) {
        return iEmployeeService.findEmployeesByRoleId(id).stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());
    }

}
