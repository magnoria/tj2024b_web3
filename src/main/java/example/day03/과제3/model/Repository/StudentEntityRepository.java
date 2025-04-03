package example.day03.과제3.model.Repository;

import example.day03.과제3.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEntityRepository extends JpaRepository<StudentEntity, Integer> {
//상속을 받아야 crud르 사용할수있음
}
