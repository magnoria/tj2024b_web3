package example.day01.과제;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryEntityRepository extends JpaRepository<LibraryEntity , Integer> {

}
