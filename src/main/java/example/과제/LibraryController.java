package example.과제;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day01/library")
@RequiredArgsConstructor
public class LibraryController {

    private  final LibraryService libraryService;

    //1. 등록
    @PostMapping
    public boolean post(@RequestBody LibraryEntity libraryEntity){

        boolean result = libraryService.post(libraryEntity);

        return result;
    }

    //2. 전체조회
    @GetMapping
    public List<LibraryEntity> findAll(){
        return libraryService.findAll();
    }

    //3. 개별 수정
    @PutMapping
    public boolean put(@RequestBody LibraryEntity libraryEntity){
        boolean result = libraryService.put(libraryEntity);
        return result;
    }

    //4. 개별 삭제
    @DeleteMapping
    public boolean delete(@RequestParam Integer bookId){
        boolean result = libraryService.delete(bookId);
        return  result;
    }

}
