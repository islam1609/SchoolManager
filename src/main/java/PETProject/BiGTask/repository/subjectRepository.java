package PETProject.BiGTask.repository;


import PETProject.BiGTask.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface subjectRepository extends JpaRepository<Subject,Long>{
}
