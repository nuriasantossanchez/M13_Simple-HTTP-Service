package springBootSimpleHTTPService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springBootSimpleHTTPService.entity.Employee;
import springBootSimpleHTTPService.repository.IEmployeeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Clase de la capa Service, implementa la interface IEmployeeService
 *
 * Anotaciones:
 * @Service
 * Indica que la clase es un "Servicio", esto es, una operación ofrecida como una interface que esta solo en el modelo,
 * sin un estado encapsulado.
 * Sirve como una especialización de @Component, lo que permite que las clases de implementacion se detecten
 * automaticamente a traves del escaneo del classpath
 *
 * @Autowired
 * Marca un constructor, campo, metodo setter o metodo de configuracion para ser detectado
 * automaticamente por la funcionalidad de inyeccion de dependencias de Spring
 *
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService{

    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Override
    public List<Employee> listEmployees() {
        return iEmployeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return iEmployeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return iEmployeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return iEmployeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        iEmployeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> findEmployeesByRoleId(Long roleId) {
        return iEmployeeRepository.findEmployeesByRoleId(roleId);
    }

}
