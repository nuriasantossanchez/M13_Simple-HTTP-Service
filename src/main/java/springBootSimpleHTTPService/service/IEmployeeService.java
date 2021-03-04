package springBootSimpleHTTPService.service;

import springBootSimpleHTTPService.entity.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Interface de la capa Service
 *
 */
public interface IEmployeeService {

    //Metodos del CRUD
    List<Employee> listEmployees(); //Listar All

    Employee saveEmployee(Employee employee); //Guarda un employee CREATE

    Optional<Employee> findEmployeeById(Long id); //Lee datos de un employee READ

    Employee updateEmployee(Employee employee); //Actualiza datos del employee UPDATE

    void deleteEmployee(Long id);// Elimina el employee DELETE

    List<Employee> findEmployeesByRoleId(Long roleId); //Busca empleados por faena
}
