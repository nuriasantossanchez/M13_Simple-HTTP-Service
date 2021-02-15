package springBootSimpleHTTPService.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springBootSimpleHTTPService.dto.EmployeeDto;
import springBootSimpleHTTPService.entity.Employee;

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
