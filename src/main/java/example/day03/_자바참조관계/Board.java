package example.day03._자바참조관계;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
public class Board {
    private int 게시물번호;
    private String 게시물제목;
    private String 게시물내용;
    //Category타입 으로 멤버 변수 선언 가능?
    private Category category; // Fk가 될수 있다. 단방향참조
    //양방향 참조, 하나의 게시물이 여러개 댓글 참조한다.
    @ToString.Exclude //해당 필드는toString 메소드에서 제외
    private List<Reply> replyList;
}
