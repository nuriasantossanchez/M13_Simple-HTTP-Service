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

/**
 * Clase de la capa Service, implementa la interface IRoleService
 *
 * Anotaciones:
 * @Service
 * Indica que la clase es un "Servicio", esto es, una operación ofrecida como una interface que esta solo en el modelo,
 * sin un estado encapsulado.
 * Sirve como una especialización de @Component, lo que permite que las clases de implementacion se detecten
 * automaticamente a traves del escaneo del classpath
 *
 * @Autowired
 * Marca un constructor, campo, metodo setter o metodo de configuracion para ser detectado
 * automaticamente por la funcionalidad de inyeccion de dependencias de Spring
 *
 */
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
