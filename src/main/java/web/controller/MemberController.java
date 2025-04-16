package web.controller;

import lombok.RequiredArgsConstructor;
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

    //http://192.168.40.25:8080/member/login
    @PostMapping("/signup") //{"memail" : "dasdsad@naver.com" ,  "mpwd" : "12342413" , "mname" : "테스트"}
    public boolean signup(@RequestBody MemberDto memberDto){
        System.out.println("MemberController.signup");
        System.out.println("memberDto = " + memberDto);

        return memberService.signup(memberDto);
    }

    // [2] 로그인
    @PostMapping("login")
    public String login (@RequestBody MemberDto memberDto){
        return memberService.login(memberDto);
    }
    // [3] 로그인된 회원 검증/ 내정보 조회
    @GetMapping("info")
    //@RequestHeader : HTTP 헤더 정보를 매핑하는 어노테이션 , JWT 정보는 HTTP 헤더에 담을 수 있다.
        //Authorization : 인증 속성 , {Authorization : token값}
    //@RequestParam : HTTP 헤더의 경로 쿼리스트링 매핑 하는 어노테이션
    //@RequestBody : HTTP 본문의 객체를 매핑 하는 어노테이션
    //@PathVariable : HTTP 헤더의 경로 값을 매핑 하는 어노테이션
    public MemberDto info(@RequestHeader("Authorization") String token){
        //http://192.168.40.25:8080/member/info
        // header : {'Authorization' : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXNkc2FkQG5hdmVyLmNvbSIsImlhdCI6MTc0NDc3MTYyNCwiZXhwIjoxNzQ0ODU4MDI0fQ.ZZf240FRRmvDi-dVB1FHUT9DrvaT8vwsnCNFtD76bB8 }
        System.out.println(token);
        return memberService.info(token);
    }

}
