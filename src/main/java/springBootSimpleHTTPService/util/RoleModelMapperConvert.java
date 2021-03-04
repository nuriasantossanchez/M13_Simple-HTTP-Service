package springBootSimpleHTTPService.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springBootSimpleHTTPService.dto.RoleDto;
import springBootSimpleHTTPService.entity.Role;

/**
 * Clase de la capa Controller
 *
 * Utiliza el objeto ModelMapper para realizar el mapeo de objetos de tipo Role
 * a objetos de tipo RoleDto.
 * Marcada con la anotacion @Autowired, la clase ModelMapper es automaticamente detectada por Spring
 */
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
