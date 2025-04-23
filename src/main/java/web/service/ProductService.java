package web.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import web.model.dto.ProductDto;
import web.model.entity.CategoryEntity;
import web.model.entity.ImgEntity;
import web.model.entity.MemberEntity;
import web.model.entity.ProductEntity;
import web.model.repository.CategoryEntityRepository;
import web.model.repository.ImgEntityRepository;
import web.model.repository.MemberEntityRepository;
import web.model.repository.ProductEntityRepository;
import web.util.FileUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    // *
    private final ProductEntityRepository productEntityRepository;
    private final MemberEntityRepository memberEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final FileUtil fileUtil;
    private final ImgEntityRepository imgEntityRepository;

    // 1. 제품등록
    public Boolean registerProduct(ProductDto productDto, int loginMno){
        // 1. 현재 회원번호의 엔티티 찾기 (연관관계) FK
        Optional<MemberEntity>  optionalmemberEntity = memberEntityRepository.findById(loginMno);
        if (optionalmemberEntity.isEmpty()){return false;} //만약에 조회된 회원 엔티티가 없으면 false
        // 2. 현재 카테고리번호의 엔티티 찾기 (연관관계) FK
        Optional<CategoryEntity> optionalCategoryEntity = categoryEntityRepository.findById( productDto.getCno());
        if (optionalmemberEntity.isEmpty()) return false; // 만약에 조회된 카테고리 엔티티가 없으면 false
        // 3. ProductDto 를 ProductEntity 변환
        ProductEntity productEntity = productDto.toEntity();
        // 4. 단방향 관계 (FK) 주입 ,cno(x) ---> CategoryEntity
        productEntity.setCategoryEntity(optionalCategoryEntity.get());
        productEntity.setMemberEntity(optionalmemberEntity.get());
        // 5. 영속성 연결
        ProductEntity saveEntity = productEntityRepository.save(productEntity);
        if (saveEntity.getPno() <= 0) return false; // 제품번호가 0 이하이면 실패
        // 6. 파일 처리 , 첨부파일이 비어있지 않으면 업로드 진행
        if (productDto.getFiles() != null && !productDto.getFiles().isEmpty()){
            // 6-1 : 여러개 첨부파일이 비어있지 않으면 업로드 진행
            for (MultipartFile file : productDto.getFiles()){
                //6-2 : FileUtil 에서 업로드 메소드 호출 (web2 에서 만든 함수들)
                String saveFileName = fileUtil.fileUpload(file);
                //6-3 : * 만약에 업로드 실패하면 트랜잭션 롤백 * @Transactional
                if (saveFileName == null){ // 6-4 : 강제  예외 발생 해서 틀랜잭션 롤백하기.
                    throw  new RuntimeException("업로드 중에 오류 발생");
                }
                //6-4 : 업로드 성공했으면 ImgEntity 만들기 , 업로드한 파일명 넣기
                ImgEntity imgEntity = ImgEntity.builder().iname(saveFileName).build();
                //6-5 : * 단방향 관계 (FK) 주입 , pno[x] --> productEntity *
                imgEntity.setProductEntity(saveEntity);
                //6-6 : ImgEntity 영속화
                imgEntityRepository.save(imgEntity);
            }
        }
        return true;
    }

    // 2. // 2. (카테고리별) 제품 전체조회 : 설계 : (카테고리조회)?cno=3 , (전체조회)?cno
    public List<ProductDto> allProducts(Long cno){ //소문자 long이면 if (cno > 0 && cno != null) 사용못함
        // 1. 조회된 결과를 저장하는 리스트 변수
        List<ProductEntity> productEntityList;
        // 2. cno에 따라 카테고리별 조회 vs 전체조회
        if (cno > 0 && cno != null){ // 2-1 : 카테고리별 조회
            productEntityList = productEntityRepository.findByCategoryEntityCno(cno);
        }else {// 2-2 : 전체조회
            productEntityList = productEntityRepository.findAll();
        }
        // 3. 조회한 결과 entity 를 dto로 변환
        return productEntityList.stream()
                .map(ProductDto :: toDto)
                .collect(Collectors.toList());
    }

    // 3. 제품 개별조회 : 설계 : ?pno=1
    public ProductDto viewProduct(long pno){
        // 1. pno에 해당하는 엔티티 조회
        Optional <ProductEntity> productEntityOptional = productEntityRepository.findById(pno);
        productEntityRepository.findById(pno);
        //2. 조회결과 없으면 null
        if (productEntityOptional.isEmpty())return null;
        //3. 조회 결과 있으면 엔티티 꺼내기  .get()
        ProductEntity productEntity = productEntityOptional.get();
        //4. 조회수 증가 , 기존 조회수 호출해서 +1 결과를 저장
        productEntity.setPview(productEntity.getPview()+1);
        //5. 조회된 엔티티를 DTO로 변환
        return ProductDto.toDto(productEntity);
    }

    // 4. 제품 개별 삭제 , +이미지 삭제
    public boolean delteProduct(long pno , int loginmno){

        // 1. pno에 해당 하는 엔티티 찾기
        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById(pno);
        // 2. 없으면 false
        if (productEntityOptional.isEmpty()) return false;
        // 3. 요청한 사람이 등록한 제품인지 확인
        ProductEntity productEntity = productEntityOptional.get();
        if (productEntity.getMemberEntity().getMno() != loginmno){
            // 만약에 제품 등록한 회원의 번호 와 현재 로그인된 회원번호가 일치하지 않으면 null
            return false;
        }
        // 4. 서버에 저장된 (업로드) 이미지들 삭제
        List<ImgEntity> imgEntityList = productEntity.getImgEntityList();
        for (ImgEntity imgEntity : imgEntityList){
            boolean result = fileUtil.fileDelete(imgEntity.getIname()); // web2 작성한 파일삭제 메소드 참고
            if (result == false){
                throw new RuntimeException("파일삭제 실패"); //트랜잭션 롤백.
            }
        }
        //5. 이미지 모두 삭제 했으면 DB삭제 , ?? 이미지 db는 삭제 안한다.
        // cascade = CascadeType.All 관계로 제품이 삭제되면 (FK)이미지 레코드로 같이 삭제한다. 만약 cascade가 안되있다면 따로 이미지 레코드도 삭제해야한다.
        productEntityRepository.deleteById(pno);
        return true;
    }

}
