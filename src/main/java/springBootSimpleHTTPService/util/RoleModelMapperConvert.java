package springBootSimpleHTTPService.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springBootSimpleHTTPService.dto.RoleDto;
import springBootSimpleHTTPService.entity.Role;

@Component
public class RoleModelMapperConvert {

    @Autowired
    private ModelMapper modelMapper;

    public RoleDto convertToDto(Role role) {
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        roleDto.setJobTitle(role.getJobEnum().getJobTitle());
        roleDto.setAnnualSalary(role.getAnnualSalary());
        return roleDto;
    }

}
