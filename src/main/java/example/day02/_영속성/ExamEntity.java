package example.day02._영속성;

import example.day02._toDto.ExamDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Entity
@Table( name = "example")
@Data
public class ExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}