package springBootSimpleHTTPService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springBootSimpleHTTPService.controller.exception.RoleNotFoundException;
import springBootSimpleHTTPService.entity.JobEnum;
import springBootSimpleHTTPService.entity.Role;
import springBootSimpleHTTPService.repository.IRoleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    IRoleRepository iRoleRepository;

    @Override
    public List<Role> listRoles() {
        return iRoleRepository.findAll();
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        return Optional.ofNullable(iRoleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id)));
    }

    @Override
    public Optional<Role> findRoleByJobTitle(String jobTitle) {
        return iRoleRepository.findRoleByJobEnum(Arrays.stream(JobEnum.values())
                .filter(e -> e.getJobTitle().equalsIgnoreCase(jobTitle))
                .findFirst()
                .orElseThrow(() -> new RoleNotFoundException(jobTitle)));
    }

}
