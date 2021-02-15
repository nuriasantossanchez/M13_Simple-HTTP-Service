package springBootSimpleHTTPService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springBootSimpleHTTPService.entity.Employee;
import springBootSimpleHTTPService.repository.IEmployeeRepository;
import springBootSimpleHTTPService.repository.IRoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService{

    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Autowired
    IRoleRepository iRoleRepository;

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
