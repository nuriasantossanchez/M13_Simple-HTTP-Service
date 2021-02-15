package springBootSimpleHTTPService.dto;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EmployeeDto extends ResponseDto{

    private Long id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private BigDecimal annualSalary;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String firstName, String lastName, String jobTitle, BigDecimal annualSalary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.annualSalary = annualSalary;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(BigDecimal annualSalary) {
        this.annualSalary = annualSalary;
    }


}
