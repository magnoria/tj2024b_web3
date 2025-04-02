package example.day01._엔티티;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity // 해당 클래스는 DB테이블 매핑
@Table(name = "task1todo") //db 테이블명 정의
public class SampleTest {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column( nullable = false , length = (100)) // not null , varchar(100)
    private String title;
    @Column(nullable = false)
    private boolean state;

    @Column(nullable = false)
    private LocalDate createat;
    @Column(nullable = false)
    private LocalTime updateat;

}
