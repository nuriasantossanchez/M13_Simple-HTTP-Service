package springBootSimpleHTTPService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springBootSimpleHTTPService.dto.RoleDto;
import springBootSimpleHTTPService.entity.Role;
import springBootSimpleHTTPService.service.IRoleService;
import springBootSimpleHTTPService.util.RoleModelAssembler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * Clase de la capa Controller.
 * La anotacion @RestController convierte a la aplicacion en un REST Service, basado en el intercambio
 * de recursos (elementos de informacion) entre componentes de la red, clientes y servidores, que
 * se comunican a traves del protocolo HTTP
 *
 * Anotaciones:
 *
 * @RestController
 * Anotacion que a su vez esta anotada con @Controller y @ResponseBody.
 * Los tipos que llevan esta anotacion se tratan como controladores donde los metodos @RequestMapping
 * asumen la semantica @ResponseBody por defecto
 *
 * @Autowired
 * Marca un constructor, campo, metodo setter o metodo de configuracion para ser detectado
 * automaticamente por la funcionalidad de inyeccion de dependencias de Spring
 *
 * @GetMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.GET).
 * El punto de acceso a la peticion sera http://localhost:8181/{path}, en este caso
 * (el puerto 8181 queda especificado en el archivo application.properties, del directorio resources)
 *
 */
@RestController
public class RoleController {

    private final IRoleService iRoleService;
    private final RoleModelAssembler roleModelAssembler;

    /**
     * Constructor de la clase, parametrizado con la interface IRoleService y la clase RoleModelAssembler,
     * que implementa la interface RepresentationModelAssembler
     * Marcado con la anotacion @Autowired, la clase controlador es automaticamente detectada por Spring
     *
     * @param iRoleService, interfaz de tipo IRoleService, implementada por la clase RoleServiceImpl,
     *                      en la que se exponen los servicios o funcionalidades accesibles via HTTP
     * @param roleModelAssembler, instancia de tipo RoleModelAssembler, convierte un objeto de dominio en un
     *                            RepresentationModel, esto es, un EntityModel que envuelve al objeto de dominio
     *                            y lo agrega enlaces
     *
     */
    @Autowired
    public RoleController(IRoleService iRoleService, RoleModelAssembler roleModelAssembler) {
        this.iRoleService = iRoleService;
        this.roleModelAssembler = roleModelAssembler;
    }

    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL http://localhost:8181/roles
     *
     * Accede a la capa de servicio RoleServiceImpl mediante su interface IRoleService
     * y hace uso del metodo 'listRoles()' para recuperar un listado de tipos RoleDto
     * en forma de ResponseEntity, esto es, agregando enlaces al objeto de dominio
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de tipos RoleDto,
     * que contiene todos los empleos disponibles en el sistema, junto con enlaces agregados
     */
    @GetMapping("/roles")
    public ResponseEntity<?> allRoles(){

        List<EntityModel<RoleDto>> roles = iRoleService.listRoles().stream()
                .map(roleModelAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<RoleDto>> collectionModel =
                CollectionModel.of(roles,
                        linkTo(methodOn(RoleController.class).allRoles()).withSelfRel());

        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);

    }

    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL http://localhost:8181/roles/{valor numerico [1-11]}
     *
     * Accede a la capa de servicio RoleServiceImpl mediante su interface IRoleService
     * y hace uso del metodo 'findRoleById(id)' para recuperar un objeto de tipo RoleDto
     * en forma de ResponseEntity, esto es, agregando enlaces al objeto de dominio
     *
     * @param id, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *            y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *            Indica el id del role/empleo que se quiere obtener
     *
     * @return objeto generico de tipo ResponseEntity, formado por un objeto de tipo RoleDto,
     * que contiene un empleo concreto disponible en el sistema, determinado por el valor numerico
     * de su id, junto con enlaces agregados
     */
    @GetMapping("/roles/{id}")
    public ResponseEntity<?> oneRole(@PathVariable(name="id") Long id) {

        Optional<Role> roleEntity = iRoleService.findRoleById(id);

        Optional<Role> role = iRoleService.findRoleById(roleEntity.get().getId());

        EntityModel<RoleDto> entityModel = roleModelAssembler.toModel(roleEntity.get());

        System.out.println("Role By Id: \n" + roleEntity);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

}
