package springBootSimpleHTTPService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import springBootSimpleHTTPService.controller.RoleController;
import springBootSimpleHTTPService.dto.RoleDto;
import springBootSimpleHTTPService.entity.Role;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Clase de la capa Controller
 *
 * Implemente la interfaz RepresentationModelAssembler
 * (pertenece al modulo de Spring HATEOAS, org.springframework.hateoas.server)
 *
 * Convierte un objeto de dominio en un RepresentationModel, que es una clase base
 * para que los DTO recopilen enlaces, un EntityModel simple que envuelve un objeto
 * de dominio y le agrega enlaces.
 *
 * Anotaciones:
 *
 * @Component
 * Indica que una clase es un "componente".
 * Estas clases se consideran candidatas para la detección automática cuando se utiliza una configuración
 * basada en anotaciones y un escaneo de classpath.
 * También se pueden considerar otras anotaciones a nivel de clase como identificación de un componente,
 * normalmente un tipo especial de componente: por ejemplo, la anotación @Repository
 *
 */
@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<RoleDto>> {

    @Autowired
    private RoleModelMapperConvert roleModelMapperConvert;

    /**
     * Metodo abstracto de la interfaz RepresentationModelAssembler
     * Convierte un objeto de tipo Role en un EntityModel de tipos RoleDto.
     * El objeto EntityModel envuelve un objeto de dominio y le agrega enlaces
     *
     * @param role, objeto de tipo Role
     * @return objeto de tipo EntityModel que envuelve a un objeto de tipo RoleDto
     * y le agrega enlaces
     */
    public EntityModel<RoleDto> toModel(Role role) {

        RoleDto roleDto = roleModelMapperConvert.convertToDto(role);


        return EntityModel.of(roleDto,
                linkTo(methodOn(RoleController.class).oneRole(role.getId())).withSelfRel(),
                linkTo(methodOn(RoleController.class).allRoles()).withRel("all"));

    }

}
