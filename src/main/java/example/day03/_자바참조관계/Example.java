package example.day03._자바참조관계;

import java.util.ArrayList;

public class Example {
    public static void main(String[] args) {

        //참조 : 실제 인스턴스(객체)의 (주소) 위치
        // 1. 인스턴스 생성
        //2. 생성된 인스턴스(주소)를 변수가 참조한다.
        Category category = new Category();
        // 참조 안의 참조 참조관계가능 .(도트연산자)
        category.set카테고리번호(1);
        category.set카테고리명("공지사항");
        //3. 변수가 참조하는 인스턴스에는 2개의 멤버변수를 참조할 수 있다.
        // category 변수가 참조 횟수 : 3개
        System.out.println(category.get카테고리번호());
        System.out.println(category.get카테고리명());
        //4. 공지사항 카테고리를 참조하는 게시물 인스턴스 생성
        Board board = Board.builder().게시물번호(1).게시물제목("안녕하세요").게시물내용("하하하")
                .category(category).build();
        //5. board 변수가 참조하는 참조가능한 횟수 :
        System.out.println(board.get게시물번호());
        System.out.println(board.get게시물제목());
        System.out.println(board.get게시물내용());
        System.out.println(board.getCategory().get카테고리번호());
        System.out.println(board.getCategory().get카테고리명());

        // 6. '공지사항' 카테고리에 1번 게시물의 댓글 등록
        Reply reply = Reply.builder().댓글번호(1).댓글내용("댓글이에요")
                .board(board) // 1번 댓글에 1번게시물 인스턴스를 대입
                .build();
        System.out.println(reply.get댓글번호());//1
        System.out.println(reply.get댓글내용());//댓글이에요
        System.out.println(reply.getBoard().get게시물번호());//1
        System.out.println(reply.getBoard().get게시물제목());//안녕하세요
        System.out.println(reply.getBoard().get게시물내용());//하하하
        System.out.println(reply.getBoard().getCategory().get카테고리번호());//1
        System.out.println(reply.getBoard().getCategory().get카테고리명());//공지사항

        //[단방향] , 데이터베이스사용 , pk-fk관계
        //7. reply 객체에서 baord객체와 category 객체 알수있다.[o] , fk 이용하여 pk join
        //7. category 객체에서 board객체와 reply 객체를 알수 없다[o] ,
        //System.out.println(category.getboard());

        //[ 양방향 ] , 데이터베이스 미사용 , 자바 가능
        category.setBoardList(new ArrayList<>());
            category.getBoardList().add(board); //양방향 대입
        board.setReplyList(new ArrayList<>());
            board.getReplyList().add(reply); //양방향 대입
        System.out.println(category.getBoardList().get(0).getReplyList().get(0));
        //*StackOverFlowError(JVM[Stack]의 메모리가 넘쳤다.)
        // -> toString() 메소드 때문에 순환참조가 된다.
        // category(toString 포함) <-----> boardList(toString포함)
        //1. 해결방안 : toString() 사용하지 않기, 또는 사용하되 양방향일 경우 한쪽 방향에 대해서만 사용
    }
}
