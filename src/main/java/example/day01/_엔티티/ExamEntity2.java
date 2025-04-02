package example.day01._엔티티;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity //해당 클래스를 DB테이블과 매핑 관계 주입(ORM 컨테이너에 저장됨 , 영속성 컨텍스트에 저장)
@Table(name = "Exam2") // DB테이블명 정의, 생략시 클래스명으로 정의된다.
public class ExamEntity2 {
    @Id // primary key 제약조건 정의
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 지원함
    private  long id;

    @Column( nullable = true , unique = false)
    private String col1;

    @Column( nullable = false , unique = true)
    private String col2;

    @Column(columnDefinition = "varchar(30)")
    private String col3;

    @Column( name = "제품명" , length = 30 , insertable = true , updatable = true)
    private String col4;

    @Column
    private LocalDate col5; //date

    @Column
    private LocalTime col6; // datetime
}
