package example.day04_과제.model.dto;


import example.day04_과제.model.entity.EquipmentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class EquipmentDto {


    private int id;
    private String name;
    private String description; //비품설명
    private String quantity; //수량
    private LocalDateTime createAt; //등록날짜
    private LocalDateTime updateAt; //수정날짜

    public EquipmentEntity toEquipmentEntity(){
        return EquipmentEntity.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .quantity(this.quantity)
                .build();
    }


}
