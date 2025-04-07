package example.day03.과제3.model.entity;

import example.day03.과제3.model.dto.StudentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "day03student")
@Builder@Data//일단 Data를 넣지 않았음
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;
    @Column
    private String sname;

    //fk 설정 , 단방향
    @ManyToOne
    private CourseEntity courseEntity;

    public StudentDto toDto(){
        return StudentDto.builder().sno(this.sno).sname(this.sname)
                .cno(this.courseEntity.getCno())// 여기 cno를 추가함, 없을시 값을 못가져와서 0으로 출력
                .build();
    }

}
