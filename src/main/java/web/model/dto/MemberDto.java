package web.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.model.entity.MemberEntity;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class MemberDto {

    private  int mno;
    private  String memail;
    private  String mpwd;
    private  String mname;
    // dto --> entity

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(mno)
                .memail(memail)
                .mpwd(mpwd)
                .mname(mname)
                .build();
    }



}
