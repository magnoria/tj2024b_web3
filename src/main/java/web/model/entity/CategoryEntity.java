package web.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity@Table(name = "category") //테이블 생성과 매핑
@Builder
@Getter@Setter@AllArgsConstructor@NoArgsConstructor // 롬복
public class CategoryEntity extends BaseTime{

    @Id //preimary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private long cno; //카테고리 식별번호

    @Column(nullable = false , length = 100) // not null, 길이 100
    private String cname; //카테고리 이름

    // * 양방향
    @OneToMany(mappedBy = "categoryEntity" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @ToString.Exclude@Builder.Default
    private List<ProductEntity> productEntityList = new ArrayList<>();

}
