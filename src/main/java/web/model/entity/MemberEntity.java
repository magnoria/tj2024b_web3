package web.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.model.dto.MemberDto;

@Entity
@Table(name = "member")
@Data@Builder
@NoArgsConstructor@AllArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String memail;
    private String mpwd;
    private String mname;
    //entity --> dto

    public MemberDto toDto(){
        return  MemberDto.builder() // 여기가 틀렸어서 안됬었음
                .mno(mno)
                .memail(memail)
                .mpwd(mpwd)
                .mname(mname)
                .build();
    }

}
