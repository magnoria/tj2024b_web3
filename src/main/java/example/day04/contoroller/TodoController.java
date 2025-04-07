package example.day04.contoroller;

import example.day04.model.dto.TodoDto;
import example.day04.model.entity.TodoEntity;
import example.day04.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day04/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    //1. 등록 // {"title" : "제목" , "content" : "내용" , "don" : "false"}
    @PostMapping
    public TodoDto todoSave(@RequestBody TodoDto todoDto){
        return todoService.todoSave(todoDto);
    }
    //2. 전체조회
    @GetMapping
    public List<TodoDto> todoFindAll(){
        return todoService.todoFindAll();
    }

    //3. 개별조회
    @GetMapping("/view")
    public TodoDto todoFindById(@RequestParam int id){
        return todoService.todoFindById(id);
    }

    //4. 개별수정 {"id" :"1" ,"title" : "제목11" , "content" : "내용11" , "done" : "true"}
    @PutMapping
    public TodoDto todoUpdate(@RequestBody TodoDto todoDto){
        return todoService.todoUpdate(todoDto);
    }

    //5. 개별삭제
    @DeleteMapping
    public boolean todoDelete(@RequestParam int id){
        return todoService.todoDelete(id);
    }

    //6. 전체조회(+ 페이징처리)
    @GetMapping("/page")
    public List<TodoDto> getFindPage( //@RequestParam(defaultValue = "초기값") : 만약에 매개변수값이 존재하지 않으면 초기값 대입
            @RequestParam(defaultValue = "1") int page, // page : 현재 조회하 페이지 번호 , 초기값 = 1
            @RequestParam(defaultValue = "3") int size){// size : 현재 조회할 페이지당 자료 개수 , 초기값 = 3
        return todoService.getFindPage(page, size);

    }

    //7. 제목 검색 조회1 (입력한 값이 일치한 제목 조회)
    @GetMapping("/search1")
    public List<TodoDto> search1(@RequestParam String title){
        return todoService.search1(title);
    }

    //8. 제목 검색 조회2 (입력한 값이 포함된 제목 조회)
    @GetMapping("/search2")
    public List<TodoDto> search2(@RequestParam String keyword){
        return todoService.search2(keyword);
    }


} // class end
