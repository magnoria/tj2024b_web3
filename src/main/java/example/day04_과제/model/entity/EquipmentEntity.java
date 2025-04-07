package example.day04_과제.model.entity;

import example.day04_과제.model.dto.EquipmentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "equipment")
@Data@Builder
@NoArgsConstructor@AllArgsConstructor
public class EquipmentEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String description; //비품설명
    @Column(nullable = false)
    private String quantity; //수량

    public EquipmentDto toDto(){
        return EquipmentDto.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .quantity(this.quantity)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();


    }


}
