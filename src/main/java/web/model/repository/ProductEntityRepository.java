package web.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.entity.ImgEntity;
import web.model.entity.ProductEntity;

import java.util.List;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {

    // 방법1. JPA 기본적인 함수 제공
    // save , finAll, findById, delete 등

    // 방법2. 쿼리메소드 , 규칙 : 명명규칙(카멜표기법)
        //findBy ~ : select하겠다.
            //findByCono[x] : 엔티티 에는 cno가 존재하지 않는다, productEntity에는 cno가 존재하지 않는다.
                    // findByCategoryEntityCno(long cno);[0] -> 이런식으로 찾아야한다.
            //findByPname[0] : [rpdictEmtotu 에는 pname 존재해서 가능
    List<ProductEntity> findByCategoryEntityCno(long cno);

    // 방법3. 네이티브 쿼리 , 규칙 : mysql 코드 , query문에서 매개변수 사용시 앞에 :(콜론)
        // select * from product where cno = :cno
    @Query(value = "select * from product where cno = :cno" , nativeQuery = true)
    List<ProductEntity> nativeQuery1(long cno);

    // 방법4* JPQL , 규칙 : 자바가만든 sql 코드가 있음

}
