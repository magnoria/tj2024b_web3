package example.day03._JPA연관관계;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data@Builder
@Entity@Table(name = "day03board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno; // 게시물 번호
    private String btitle; // 게시물 제목
    private String bcontent; // 게시물 내용
    // + 단방향, 카테고리 참조, Fk필드
    @ManyToOne//fk필드 선언 방법
    private Category category;

    // + 양방향
    @ToString.Exclude //순환참조 방지
    @Builder.Default// 빌더패턴 사용시 초기값 대입
    @OneToMany( mappedBy = "board" , cascade = CascadeType.ALL , fetch = FetchType.EAGER) // 제약조건 옵션 : 만약에 PK가 변경되면 FK도 변경되도록 할 수있다.
    private List<Reply> replyList = new ArrayList<>();
}
