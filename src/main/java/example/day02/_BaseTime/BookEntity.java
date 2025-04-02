package example.day02._BaseTime;


import jakarta.persistence.*;
import lombok.Data;

@Entity// 해당 클래스는 DB테이블과 매핑
@Table(name = "day02book") // db테이블명 정의
@Data
public class BookEntity extends BaseTime {

    @Id// primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int 도서번호; //auto_increment 자동번호 부여하기 위한 int타입 선정 / pk 선정

    @Column(nullable = false, length = 30) //not null , varchar(30)
    private String 도서명;

    @Column(nullable = false , length = 100) //not null , varchar(100)
    private String 저자;

    @Column(nullable = false ,length = 50) //not null , varchar(50)
    private String 출판사;

    @Column
    private int 출판연도;
}