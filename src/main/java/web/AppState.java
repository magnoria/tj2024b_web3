package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication // 스프링부트 기초 설정
@EnableJpaAuditing // JPA 감사 기능 활성화
public class AppState {
    public static void main(String[] args) {
        SpringApplication.run(AppState.class);
    }
}
