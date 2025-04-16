package web.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // Spring 컨테이너에 빈 등록 , 빈을 주입받을때 쓰는게 @Autowilde
public class JwtUtil {

    // 비밀키 알고리즘 : HS256알고리즘, HS512알고리즘
    //private String secretKey = "인코딩된 HS512 비트 키";
    // (1) 개발자가 임의로 지정한 키 : private String secretKey = "qP9sLxV3tRzWn8vMbKjUyHdGcTfEeXcZwAoLpNjMqRsTuVyBxCmZkYhGjFlDnEpQzFgXt9pMwX8Sx7CtQ5VtBvKmA2QwE3D";
    // (2) 라이브러리 이용한 임의(랜덤)키 : -> 서버를 껐다 키면 계속 바뀜
        // import java.security.Key;
        //Keys.secretKeyFor(SignatureAlgorithm.알고리즘명);
    private Key secretkey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // [1] JWT 토큰 발급 , 사용자의 이메일을 받아서 토큰 만들기
    public String createToken(String memail){
        return Jwts.builder()
                // 토큰에 넣을 내용물 , 로그인 성공한 회원의 이메일을 넣는다.
                .setSubject( memail )
                // 토큰이 발근된 날짜 , new Date() : 자바에서 제공하는 현재 날짜 클래스
                .setIssuedAt(new Date())
                // 토큰 만료시간 , 밀리초(1000/1) new Date(System.currentTimeMillis()): 현재시간의 밀리초
                // new Date(System.currentTimeMillis() + (1000 * 초 * 분 * 시)) // 만약 1분으로 하고 싶을때 예시 (1000 * 60)
                .setExpiration( new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24))) // 1일의 토큰 유지 기간
                .signWith(secretkey) // 지정한 비밀키로 암호화 한다.
                .compact(); //위 정보로 JWT 토큰 생성하고 반환한다.
    }

    // [2] JWT 토큰 검증
    public String validateToken(String token){
        try {
            Claims claims = Jwts.parser() // 1. parser() : JWT 토큰 검증하기 위한 함수
                    .setSigningKey(secretkey) // 2. .setSigningKey(비밀키) : 검증하기 위한 비밀키 넣기
                    .build()//3. 검증 실행할 객체 생성 , 검증 실패시 예외 발생
                    .parseClaimsJws(token) //4. 검증에 사용할 토큰 지정 검증할 토큰해석 , 실패시 예외 발생
                    .getBody(); // 검증된 (claims) 객체 생성후 반환
            //clAims 안에는 다양한 토큰 정보가 들어있다.
            System.out.println(claims.getSubject()); // 토큰에 저장된 회원 이메일
            return claims.getSubject();
        }catch(ExpiredJwtException e){
            //토큰이 만료 되었을때 예외 클래스
            System.out.println(">> JWT 토큰 기한 만료 :" + e);
        }catch (JwtException e){
            // 그외 모든 토큰 예외 클래스
            System.out.println(">> JWT 예외 :" + e);
        }
        return null; //유효하지 않은 토큰인 경우 또는 오류 발생시 null 반환
    }
}
