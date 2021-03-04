package springBootSimpleHTTPService.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springBootSimpleHTTPService.dto.EmployeeDto;
import springBootSimpleHTTPService.entity.Employee;

/**
 * Clase de la capa Controller
 *
 * Utiliza el objeto ModelMapper para realizar el mapeo de objetos de tipo Employee
 * a objetos de tipo EmployeeDto.
 * Marcada con la anotacion @Autowired, la clase ModelMapper es automaticamente detectada por Spring
 */
@Component
public class EmployeeModelMapperConvert {

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setJobTitle(employee.getRole().getJobEnum().getJobTitle());
        employeeDto.setAnnualSalary(employee.getRole().getAnnualSalary());
        return employeeDto;
    }

}
