package example.day01._리포지토리;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 엔티티(테이블)를 조작(DML -> crud를 말함) 하는 인터페이스
// 해당 인터페이스에 JPA 엔티티 상속 JpaRepository<조작할엔티티클래스명 , 해당엔티티의 ID타입>
//<> : 제네릭
@Repository // 스프링 컨테이너에 빈 등록
public interface ExamEntityRepository extends JpaRepository<ExamEntity , String> {
}

// CRUD 메소드
// 1. .save(저장할엔티티객체);
//      : 존재하지 않은 PK이면 INSERT , 존재하는 PK이면 Update
//      반환값 : INSERT/UPDATE 이후 영속(연결/매핑)된 객체(엔티티)

//2. .findAll();
//      : 모든 엔티티를 select 한다.
//      반환값 : 리스트 타입으로 반환한다.

//3. .findById(조회할 pk값)
//      :pk 값과 일치하는 엔티티를 select 한다.
//      반환값 : Optional<엔티티>


//4. .deleteById(삭제할pk값)
//       :pk값과 일치하는 엔티티를 delete 한다.
//      반환값 : void(없다)

//5. .count()
//       : 레코드(엔티티) 전체 개수 반환
//       반환값 : long


//6. .existsById( 조회할pk값)
//     : pk값과 일치하는 엔티티가 존재하면 true , 아니면 false

// Optional 클래스 : null 관련된 메소드를 제공하는 클래스
// -> nullPointerException 방지하고자 객체를 포장하는 클래스
// 주요 메소드
// 1. .isPresent() : null 이면 false , 객체있으면 true
// 2. .get() : 객체 반환
// 3. .orElse(null일때 값) : 객체를 반환하는데 null이면 지정된 값 반환
// 4. .orElseThrow(예외객체) : 객체를 반환하는데 null이면 예외 발생