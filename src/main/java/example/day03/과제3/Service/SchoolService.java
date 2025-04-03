package example.day03.과제3.Service;

import example.day03.과제3.model.Repository.CourseEntityRepository;
import example.day03.과제3.model.Repository.StudentEntityRepository;
import example.day03.과제3.model.dto.CourseDto;
import example.day03.과제3.model.entity.CourseEntity;
import example.day03.과제3.model.dto.StudentDto;
import example.day03.과제3.model.entity.StudentEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {

    private final CourseEntityRepository courseEntityRepository;
    private final StudentEntityRepository studentEntityRepository;

    //1. 과정등록
    public boolean post(CourseDto courseDto){
        System.out.println("Controller.post");
        System.out.println("courseDto = " + courseDto);

        CourseEntity course = courseDto.toEntity();

        courseEntityRepository.save(course);

        if(course.getCno() > 0){return true;}
        return false;
    }

    //2.과정 전체조회

    public List<CourseDto> SchoolGet(){
        System.out.println("SchoolController.get");
        List<CourseEntity> courseEntityList = courseEntityRepository.findAll(); // 생성자 안만들면 오류 뜸

        List<CourseDto> courseDtoList =
        courseEntityList.stream().map(entity -> entity.courseDto() )
                .collect(Collectors.toList());
        return courseDtoList;
    }

    //3. 특정 과정에 수강생 등록
    public boolean post(StudentDto studentDto){
        //1. 학상 dto --> 학생 엔티티 변환
        StudentEntity studentEntity = studentDto.toStudent();
        //2. 학생 엔티티 save
        StudentEntity saveEntity = studentEntityRepository.save(studentEntity); //여기서 등록이 끝난다
        if(saveEntity.getSno() < 1)return false;
        //3. 특정한 과정 엔티티 조회 , cno 를 이용하여 과정 엔티티 찾기
        CourseEntity courseEntity = courseEntityRepository.findById(studentDto.getCno()).orElse(null);
        if (courseEntity == null)return false;
        //4. 등록된 학생 엔티티의 특정한 과정 엔티티 대입 <FK대입>
        saveEntity.setCourseEntity(courseEntity);//단방향 멤버변수에 과정엔티티 대입하기.(fk대입)
        //5.
        return true;

    }

    //4. 특정 과정에 수강생 특정

    public List<StudentDto> studentGet(int cno){
        System.out.println("SchoolController.get");
        System.out.println();
        //1. cno 이용하여 과정 엔티티 찾기

        CourseEntity courseEntity = courseEntityRepository.findById(cno).orElse(null);
        if (courseEntity == null) return null;
        //2. 조회한 과정 엔티티 안에 참조중인 학생목록
        List<StudentEntity> studentEntityList = courseEntity.getStudentEntityList();
        //3. dto 변환
        List<StudentDto> studentDtoList = studentEntityList.stream()
                .map( entity -> entity.toDto())
                .collect(Collectors.toList());

        //4.
        return studentDtoList;
    }


}
