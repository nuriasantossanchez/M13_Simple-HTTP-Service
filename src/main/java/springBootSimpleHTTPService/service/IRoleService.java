package springBootSimpleHTTPService.service;

import springBootSimpleHTTPService.entity.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<Role> listRoles(); //Listar All

    Optional<Role> findRoleById(Long id);

    Optional<Role> findRoleByJobTitle(String jobTitle);
}
