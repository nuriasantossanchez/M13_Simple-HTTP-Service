package springBootSimpleHTTPService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springBootSimpleHTTPService.entity.JobEnum;
import springBootSimpleHTTPService.entity.Role;

import java.util.Optional;

/**
 * Interface de la capa Repository, extiende JpaRepository
 *
 */
@Repository
@Transactional
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByJobEnum(JobEnum jobEnum);

}
