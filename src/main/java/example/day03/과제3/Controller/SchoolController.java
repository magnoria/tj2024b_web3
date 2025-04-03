package example.day03.과제3.Controller;

import example.day03.과제3.model.dto.CourseDto;
import example.day03.과제3.Service.SchoolService;
import example.day03.과제3.model.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day03/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;
    //1. 과정등록
    @PostMapping("/course")
    public boolean post(@RequestBody CourseDto courseDto){
        System.out.println("Controller.post");
        System.out.println("courseDto = " + courseDto);
        return schoolService.post(courseDto);
    }
    //2.과정 전체조회
    @GetMapping("/course/find")
    public List<CourseDto> SchoolGet(){
        System.out.println("SchoolController.get");
        return schoolService.SchoolGet();
    }

    //3. 특정 과정에 수강생 등록
    @PostMapping("/student")
    public boolean post(@RequestBody StudentDto studentDto){
        System.out.println("SchoolController.post");
        System.out.println("studentDto = " + studentDto);
        return schoolService.post(studentDto);
    }

    //4. 특정 과정에 수강생 전체 조회
    @GetMapping("/student/find")
    public List<StudentDto> studentGet(@RequestParam int cno){
        System.out.println("SchoolController.get");
        System.out.println();
        return schoolService.studentGet(cno);
    }


}
