package example.day03.과제3.model.entity;

import example.day03.과제3.model.dto.CourseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "day03course")
@Builder@Data
@NoArgsConstructor// 빈생성자
@AllArgsConstructor// 전체 매개변수 생성자
public class CourseEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    @Column
    private String cname;

    // 양방향
    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<StudentEntity> studentEntityList = new ArrayList<>();

    //dto 연결
    public CourseDto courseDto(){
        return  CourseDto.builder().cno(this.cno).cname(this.cname).build();
    }
}
