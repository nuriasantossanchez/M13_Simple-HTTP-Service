package springBootSimpleHTTPService.dto;

import java.math.BigDecimal;

public class RoleDto extends ResponseDto{

    private Long id;
    private String jobTitle;
    private BigDecimal annualSalary;

    public RoleDto() {
    }

    public RoleDto(Long id, String jobTitle, BigDecimal annualSalary) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.annualSalary = annualSalary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
