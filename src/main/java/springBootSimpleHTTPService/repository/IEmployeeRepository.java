package springBootSimpleHTTPService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springBootSimpleHTTPService.entity.Employee;

import java.util.List;

/**
 * Interface de la capa Repository, extiende JpaRepository
 *
 */
@Repository
@Transactional
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeesByRoleId(Long roleId);

}
