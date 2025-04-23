package web.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import web.model.entity.ImgEntity;
import web.model.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString // 여기다 ToString 을 만들시 productDto = web.model.dto.ProductDto@adcae6  System.out.println("token = " + token + ", productDto = " + productDto); 시 주소값이 안나옴
@Getter@Setter@Builder
@AllArgsConstructor@NoArgsConstructor
public class ProductDto {

    // * 제품 등록할때 필요한 필드
    private String pname;       // - 제품명
    private String pcontent;    // - 제품설명
    private int pprice;         // - 제품가격
    private List<MultipartFile> files = new ArrayList<>(); // - 제품이미지들
    private long cno; // -제품카테고리 번호
    // * 제품 조회할때 필요한 필드
    private long pno; // 제품 번호
    private int pview; // 제품 조회수
    private String memail; // 제품 등록한 회원아이디
    private String cname; // 제품 카테고리명
    private List<String> images = new ArrayList<>(); // 이미지들

    // * toEntity : 제품 등록시 사용
    public ProductEntity toEntity(){
        return ProductEntity.builder()
                .pname(this.pname)
                .pcontent(this.pcontent)
                .pprice(this.pprice)
                //cno 대신 CategoryEntity 넣기
                // mno 대신 MemberEntity 넣기
                .build();
    }

    //* toDto : 제품전체조회, 제품개별 조회 사용
    public static ProductDto toDto(ProductEntity productEntity){
        return ProductDto.builder()
                .pname(productEntity.getPname())
                .pcontent(productEntity.getPcontent())
                .pprice(productEntity.getPprice())
                .cno(productEntity.getCategoryEntity().getCno())
                .pno(productEntity.getPno())
                .pview(productEntity.getPview())
                .memail(productEntity.getMemberEntity().getMemail()) //등록자 회원 아이디 조회
                .cno(productEntity.getCategoryEntity().getCno()) // 카테고리
                .cname(productEntity.getCategoryEntity().getCname()) // 카테고리
                .images(// 제품이미지들을 조회
                        productEntity
                                .getImgEntityList()
                                .stream().map(ImgEntity::getIname)
                                .collect(Collectors.toList())
                ) // 이부분 잘 보기
                .build();
    }

}
