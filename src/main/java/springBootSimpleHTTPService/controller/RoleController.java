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

@RestController
public class RoleController {

    private final IRoleService iRoleService;
    private final RoleModelAssembler roleModelAssembler;


    @Autowired
    public RoleController(IRoleService iRoleService, RoleModelAssembler roleModelAssembler) {
        this.iRoleService = iRoleService;
        this.roleModelAssembler = roleModelAssembler;
    }

    @GetMapping("/roles")
    public ResponseEntity<?> allRoles(){

        List<EntityModel<RoleDto>> roles = iRoleService.listRoles().stream()
                .map(roleModelAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<RoleDto>> collectionModel =
                CollectionModel.of(roles,
                        linkTo(methodOn(RoleController.class).allRoles()).withSelfRel());

        System.out.println("ALL Roles");

        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);

    }

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
