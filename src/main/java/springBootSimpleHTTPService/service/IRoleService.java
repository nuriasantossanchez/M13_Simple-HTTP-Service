package springBootSimpleHTTPService.service;

import springBootSimpleHTTPService.entity.Role;

import java.util.List;
import java.util.Optional;

/**
 * Interface de la capa Service
 *
 */
public interface IRoleService {

    List<Role> listRoles(); //Listar All

    Optional<Role> findRoleById(Long id); //Lee datos de un role READ

    Optional<Role> findRoleByJobTitle(String jobTitle); //Busca un role por el nombre de empleo
}
