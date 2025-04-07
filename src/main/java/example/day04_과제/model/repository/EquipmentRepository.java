package example.day04_과제.model.repository;

import example.day04_과제.model.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Integer> {

    // 하나 부분 지정 검색
    @Query(value = "select * from equipment where name = :name", nativeQuery = true)
    List<EquipmentEntity> findNameNative(String name);

    // keyword를 포함한 전체 검색
    @Query(value = "select * from equipment where name like %:keyword%" , nativeQuery = true)
    List<EquipmentEntity> findNativeSearch(String keyword);

    @Modifying
    @Query(value = "delete from equipment where name = :name" , nativeQuery = true)
    int deleteByNative(String name);

    @Modifying
    @Query(value = "update equipment where name = :name where id = :id" , nativeQuery = true)
    int updateByNative( int id , String name);


}
