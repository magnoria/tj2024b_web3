package example.day04_과제;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // JPA 감사 기능 활성화 = 레코드(영속성) 등록/수정 시점 감지
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);

    }
}
