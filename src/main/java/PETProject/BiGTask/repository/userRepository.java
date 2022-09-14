package PETProject.BiGTask.repository;

import PETProject.BiGTask.model.Role;
import PETProject.BiGTask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface userRepository extends JpaRepository<User,Long>{
    User findAllByEmail(String email);
    List<User> findAllByRoles(Role role);
}
