package PETProject.BiGTask.repository;

import PETProject.BiGTask.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface roleRepository extends JpaRepository<Role,Long>{
    Role findByRole(String role);
}
