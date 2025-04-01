package example._리포지토리;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day01/jpa")
@RequiredArgsConstructor //http://localhost:8080/day01/jpa
public class ExamController {

    private final ExamService examService;
    //1. C : 등록
    @PostMapping
    public boolean post(@RequestBody ExamEntity examEntity){
        //(영속전) ExamEntity
        boolean result = examService.post(examEntity);

        return result;
    }
    //2. R : 전체조회
    @GetMapping
    public List<ExamEntity> findAll(){
        return examService.findAll();
    }

    //3. U : 수정
    @PutMapping
    public boolean put(@RequestBody ExamEntity examEntity){
        boolean result = examService.put(examEntity);
        return result;
    }

    //4. D : 삭제
    @DeleteMapping
    public boolean delete(@RequestParam String id){
        boolean result = examService.delete(id);
        return result;
    }
}
