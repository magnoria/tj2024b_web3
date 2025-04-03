package example.day03._자바참조관계;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reply {
    private int 댓글번호;
    private String 댓글내용;
    private Board board; //FK가 될수있다. 단방향참조

}
