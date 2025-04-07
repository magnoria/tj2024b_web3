package example.day04.model.repository;

import example.day04.model.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

    //JPA repository
    //1. .save() 2. .findById() 3. .findAll() 4. .deleteById()  등등 미리 만들어진 CRUD 메소드 제공
    //2. 쿼리메서드(JPQL 이용한 메소드 이름 기반으로 자동 생성)// ================
    //Spring JPA에서 SQL 문장을 직접 작성하지 않고 메소드 이름으로 쿼리 생성한다. <카멜표기법사용해야함>
    // ***메소드 명명 규칙 존재***
    //1. findBy필드명 : 조회
    //2. findBy필드명Containing :
    List<TodoEntity> findByTitle(String title);
        // List<TodoEntity> : 조회결과를 List 타입으로 받겠다.
        // findByTitle : findBy필드명 , title 필드를 select(조회)한다. *주의할점 : 카멜표기법과 명명 규칙을 따라야 한다.
        // (String title) : 조회 조건
        // mybatis : select * from todo where title = ${title}


    List<TodoEntity> findByTitleContaining(String keyword);
        // mybatis : select * from todo where title like %${title}%

        //3. findBy필드명And필드명 : 두 조건을 조회 ,And/Or
    List<TodoEntity> findByTitleAndContent(String title, String content);
        // mybatis : select * from todo where title = ${title} and content = ${content}

        //4. existsBy필드명 : 조건에 맞는 엔티티 여부(true/false)조회 , 반환타입 : boolean
    boolean existsByTitle(String title);
        // mybatis : select exists(select 1 from todo where title = ${title})

        //5. countBy필드명 : 조건에 맞는 엔티티 개수 조회 , 반환타입 : long
    long countByTitle(String title);

        //6. deleteBy필드명 : 조건에 맞는 엔티티 삭제, 반환타입 : void
    void deleteByTitle(String title);

    //3. 네이티브쿼리( SQL 직접작성 ) //==================================
    // Spring JPA 에서 SQl 문법을 직접 작성하여 시행 한다.
    //***@Query(valu="sql문" , nativequery = true)***
        // ->Query 는 select를 위한 어노테이션 이므로 insert,update,delete 할 경우에는 @Modifying 같이 사용한다.
    // SQL 문의 매개변수를 작성시에는 : 매개변수명 작성하여 매개변수를 대입할 수 있다.
        //1.
    @Query(value = "select * from todo where title = :title" , nativeQuery = true)
    List<TodoEntity> findByTitleNative(String title);
        // List<TodoEntity> : 조회결과를 List 타입으로 받겠다.
        //findByTitleNavtive : 규칙이 없으므로 아무거나
        //(String title) : 조회 조건으로 SQL 문법의 매개변수
        //2.
    @Query(value = "select * from todo where title like %:keyword%",nativeQuery = true)
    List<TodoEntity> findByTitleNativeSearch(String keyword);

        //3. delete
    @Modifying //네이티브 (@Query)쿼리 사용시 select 만 지원하므로 insert/update/delete 쿼리는 @Modifying와 같이 사용된다
    @Query(value = "delete from todo where title = :title" , nativeQuery = true)
    int deleteByNative(String title);


        //4. update
    @Modifying
    @Query(value = "update todo set title = :title where id = :id", nativeQuery = true)
    int updateByNative( int id, String title);


}
