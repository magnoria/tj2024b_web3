package example.day03.과제3.model.Repository;

import example.day03.과제3.model.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseEntityRepository  extends JpaRepository<CourseEntity, Integer> {
}
