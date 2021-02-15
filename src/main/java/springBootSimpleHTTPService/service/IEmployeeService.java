package springBootSimpleHTTPService.service;

import springBootSimpleHTTPService.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

    //Metodos del CRUD
    List<Employee> listEmployees(); //Listar All

    Employee saveEmployee(Employee employee);	//Guarda un employee CREATE

    Optional<Employee> findEmployeeById(Long id); //Leer datos de un employee READ

    Employee updateEmployee(Employee employee); //Actualiza datos del employee UPDATE

    void deleteEmployee(Long id);// Elimina el employee DELETE

    List<Employee> findEmployeesByRoleId(Long roleId); //buscar employee por faena
}
