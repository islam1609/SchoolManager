package PETProject.BiGTask.repository;

import PETProject.BiGTask.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface typeRepository extends JpaRepository<Type,Long> {
}
