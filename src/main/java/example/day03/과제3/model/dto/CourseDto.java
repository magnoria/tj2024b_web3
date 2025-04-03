package example.day03.과제3.model.dto;

import example.day03.과제3.model.entity.CourseEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {
    private int cno;
    private String cname;

    //엔티티 연결
    public CourseEntity toEntity(){
        return CourseEntity.builder().cno(this.cno).cname(this.cname).build();
    }

}
