package web.model.dto;

import lombok.*;
import web.model.entity.CategoryEntity;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Builder@ToString
public class CategoryDto {
    private long cno;
    private String cname;

    // * toEntity : 주로 저장용

    // * toDto : 주로 호출용도
    public static  CategoryDto toDto(CategoryEntity categoryEntity){
        return CategoryDto.builder()
                .cno(categoryEntity.getCno())
                .cname(categoryEntity.getCname())
                .build();
    }
}
