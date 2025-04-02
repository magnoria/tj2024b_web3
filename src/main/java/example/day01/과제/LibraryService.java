package example.day01.과제;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryEntityRepository libraryEntityRepository;
    //등록
    public boolean post(LibraryEntity libraryEntity){

        LibraryEntity libraryEntity1 = libraryEntityRepository.save(libraryEntity);
        return true;
    }

    //전체조회
    public List<LibraryEntity> findAll(){
        return libraryEntityRepository.findAll();
    }

    //개별 수정
    @Transactional
    public boolean put(LibraryEntity libraryEntity){
        Optional<LibraryEntity> optionalLibraryEntity = libraryEntityRepository.findById(libraryEntity.getBookId());

        if (optionalLibraryEntity.isPresent()){
            LibraryEntity entity = optionalLibraryEntity.get();
            entity.setBook_name(libraryEntity.getBook_name());
            entity.setBook_writer(libraryEntity.getBook_writer());
            entity.setBook_publisher(libraryEntity.getBook_publisher());
            entity.setBook_date(libraryEntity.getBook_date());
            return true;
        }
        return false;
    }

    //개별삭제
    public boolean delete(Integer book_Id){
        libraryEntityRepository.deleteById(book_Id);
        System.out.println(libraryEntityRepository.existsById(book_Id));
        return true;
    }
}
