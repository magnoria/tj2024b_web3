package web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.dto.MemberDto;
import web.service.MemberService;

@RestController // Spring MVC2 controller
@RequestMapping("/member") // 공통url 정의
@RequiredArgsConstructor // final(수정불가능) 필드의 생성자 자동 생성
// -> 관례적으로 클래스 내부에서 사용하는 모든 필드들을 수정불가능 상태로 사용한다.
@CrossOrigin("*")
public class MemberController {
    // -> 관레적으로 다른곳에서 해당하는 필드를 수정 못하도록 final 사용한다.(안전성 보장)
    // -> 즉 final 사용시 @RequiredArgsConstructor 때문에 @Autowired 안만들어도 된다.
    private final MemberService memberService;

    //http://localhost:8080/member/signup
    @PostMapping("/signup") //{"memail" : "dasdsad@naver.com" ,  "mpwd" : "12342413" , "mname" : "테스트"}
    public ResponseEntity<Boolean> signup(@RequestBody MemberDto memberDto){
        System.out.println("MemberController.signup");
        System.out.println("memberDto = " + memberDto);
        boolean result = memberService.signup(memberDto);
        if (result){
            // * 개발자 마음대로 원하는 응답 코드를 반환 할 수 있다.
            return ResponseEntity.status(201).body(true); //201(create ok 뜻) true(반환값)
        }else {
            return  ResponseEntity.status(400).body(false); //400(bad request) 잘못된 요청
        }

    }

    // [2] 로그인 { "memail" : "qwe@naver.com" , "mpwd" : "qwe" }   // redis 키고 테스트 해야함 없으면 오류 뜸
    @PostMapping("login") //// http://localhost:8080/member/login
    public ResponseEntity<String> login (@RequestBody MemberDto memberDto){
        String token =  memberService.login(memberDto);
        if (token != null){// 만약에 토큰이 존재하면(로그인 성공)
            return  ResponseEntity.status(200).body(token); //200 코드를 보낼 수 있다.
        }else {
            return  ResponseEntity.status(401).body("로그인실패");
        }
    }
    // [3] 로그인된 회원 검증/ 내정보 조회
    @GetMapping("info")
    //@RequestHeader : HTTP 헤더 정보를 매핑하는 어노테이션 , JWT 정보는 HTTP 헤더에 담을 수 있다.
        //Authorization : 인증 속성 , {Authorization : token값}
    //@RequestParam : HTTP 헤더의 경로 쿼리스트링 매핑 하는 어노테이션
    //@RequestBody : HTTP 본문의 객체를 매핑 하는 어노테이션
    //@PathVariable : HTTP 헤더의 경로 값을 매핑 하는 어노테이션
    public ResponseEntity<MemberDto> info(@RequestHeader("Authorization") String token){
        //http://192.168.40.25:8080/member/info
        // header : {'Authorization' : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXNkc2FkQG5hdmVyLmNvbSIsImlhdCI6MTc0NDc3MTYyNCwiZXhwIjoxNzQ0ODU4MDI0fQ.ZZf240FRRmvDi-dVB1FHUT9DrvaT8vwsnCNFtD76bB8 }
        System.out.println(token);
        MemberDto memberDto =  memberService.info(token);
        if (memberDto != null){
            return ResponseEntity.status(200).body(memberDto); // 200 ok 와 조회된회원정보 반환
        }else {
            return ResponseEntity.status(403).build(); // 403 과 NoContent(자료없음)
        }
    }

    // [4] 로그아웃 , 로그아웃 할 토큰 가져오기.
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String token ) {
        memberService.logout( token );
        return ResponseEntity.status(204).build(); // 204 : 성공 했지만 반환할 값이 없다는 뜻
    }

    // [5] 실시간 최근 24시간내 로그인 한 접속자 수
    @GetMapping("/login/count")
    public ResponseEntity<Integer> loginCount(){
        int result =  memberService.loginCount();
        return ResponseEntity.status(200).body(result); // 200 : 요청 성공 뜻 과 응답 값 반환
    }

}
