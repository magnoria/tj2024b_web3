package example.day03._JPA연관관계;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data@Builder
@Entity // 해당클래스는 데이터베이스와 영속관계로 사용할 예정
@Table(name = "day03category") //DB테이블명 정의
public class Category {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno; // 카테고리 번호
    private String cname; // 카테고리명'

    // + 양방향, 게시물 여러개 참조
    //@OneToMany( mappedBy = "단방향의멤버변수명") //양방향 참조테이블은 자바에서만 참조한다.
    @OneToMany( mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.EAGER) // 제약조건 옵션 : 만약에 PK가 변경되면 FK도 변경되도록 할 수있다.
    private List<Board> boardList = new ArrayList<>();



}
/*
    - 영속성 제약조건 옵션 (1.cascade 2.fetch)
    [1]@OneToMany( cascade = CascadeType.XXX)
    1.cascade = CascadeType.ALL : 부모[PK]가 삭제/수정/저장 되면 자식[FK]도 같이 삭제/수정/저장 , REMOVE/MERGE/PERSIST
    2.cascade = CascadeType.REMOVE : 부모[PK]가 삭제 되면 자식[FK]도 같이 삭제됨.REMOVE
    3.cascade = CascadeType.MERGE : 부모[PK]가 수정 되면 자식[FK]도 같이 수정됨.REMOVE
    4.cascade = CascadeType.DETACH : 부모[PK]가 영속성 해지하면 자식[FK]도 같이 해제됨.DETACH
    5.cascade = CascadeType.PERSIST : 부모[PK]가 저장되면 자식[FK]도 같이 저장됨.PERSIST
    6.cascade = CascadeType.REFRESH : 부모[PK]가 새롭게 값을 불러올때 자식[FK]도 같이 새로 불러온다 .REFRESH(새로고침)

    [2]@OneTomany(fetch = FetchType.XXX), @ManyToOne(fetch = FetchType.xxx) 사용가능
    1. fetch = FetchType.EAGER : 즉시 로딩 (기본값) : 해당 엔티티를 조회(.findXX())할때 참조되는 모든 객체를 즉시 불러온다.
        특징 : 초기 로딩이 느리다. 불필요한 엔티티까지 모두 가져오기때문에 메모리 로드에 기능저하가 올 수 있다.

    2. fetch = FetchType.LAZY : 지연 로딩 : 해당 엔티티를 조회할때 참조되는 객체를 불러오지 않고 .getXX() 등 참조할때 참조되는 객체를 불러온다.
        특징 : 초기 로딩이 빠르다. 메모리 사용량을 적절하게 사용하므로 성능 최적화를 할 수 있다.

*/