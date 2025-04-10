package todo.service;

import todo.model.dto.TodoDto;
import todo.model.entity.TodoEntity;
import todo.model.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    //1. 등록
    public TodoDto todoSave(TodoDto todoDto){
        //1. Dto 를 entity 변환하기
        TodoEntity todoEntity = todoDto.todoEntity();
        //2. entity를 save(영속화/db레코드 매칭/등록)한다.
        TodoEntity saveEntity = todoRepository.save(todoEntity);
        //3. save 로 부터 반환된 엔티티(영속화)된 결과가 존재하면
        if (saveEntity.getId() >1) {return saveEntity.todoDto();}// entity를 dto로 변환하여 반환
        else {return null; // null 반환
        }
    }

    //2. 전체조회
    public List<TodoDto> todoFindAll(){



        // [방법1] 일반 반복문 =================================
        // 1. 모든 entity 조회, findAll()
        List<TodoEntity> todoEntityList = todoRepository.findAll();
        // 2. 모든 entity 리스트를  dto 리스트로 변환한다.
        List<TodoDto> todoDtoList = new ArrayList<>();//2-1 : dto 리스트 생성한다.
        for (int i = 0; i < todoEntityList.size(); i++){ // 2-2 : entity 리스트를 순회
            TodoDto todoDto = todoEntityList.get(i).todoDto(); //2-3 : index번째entity를 dto로 변환
            todoDtoList.add(todoDto); //2-4 : dto 리스트에 저장
        }//for end
        //3. 결과 반환
        return todoDtoList;
        //[방법2] stream ==================================//
        //return todoRepository.findAll().stream().map(TodoEntity :: todoDto).collect(Collectors.toList());

    }// f end

    public TodoDto todoFindById(int id){
        //1. pk(식별번호) 이용한 entity  조회하기
        // Optional 클래스 : null 제어하는 메소드들을 제공하는 클래
        Optional<TodoEntity> optional =
                todoRepository.findById(id);
        //2. 조회한 결과가 존재하면, .isPresnet()
        if (optional.isPresent()){
            //3. Optional 에서 entity 꺼내기
            TodoEntity todoEntity = optional.get();
            //4. dto로 변환
            TodoDto todoDto = todoEntity.todoDto();
            //5. 반환
            return todoDto;
        }
        return null;
        //[방법2] stream =========================================
//        return todoRepository.findById(id)
//                .map(TodoEntity::todoDto) //Optional 의 데이터가 null이 아니면 map을 실행하여 dto 변환후 반환
//                .orElse(null); //Optional 의 데이터가 null이면 null 반환 null말고 원하는걸 써도 됨

    }// f end

    //4. 개별수정 + Transactional 필요
    public TodoDto todoUpdate( TodoDto todoDto){
        //[방법1] 일반적인========================================
        //1. 수정할 엔티티를 조회한다
        Optional<TodoEntity> optional
            = todoRepository.findById(todoDto.getId());
        //2. 존재하면 수정 하고 존재하지 않으면 null 반환, .isPresent()
        if (optional.isPresent()){
            //3. 엔티티 꺼내기
            TodoEntity todoEntity = optional.get();
            //4. 엔티티 수정하기
            todoEntity.setTitle(todoDto.getTitle());
            todoEntity.setContent(todoDto.getContent());
            todoEntity.setDone(todoDto.isDone()); //boolean의 getter 는 isXXX() 사용
            return todoEntity.todoDto();
        }
        return null;
        //[방법2] stream ===============================
//        return todoRepository.findById(todoDto.getId())
//                .map(// findById 결과 optional 데이터가 존재하면
//                        entity -> { // 람다식 함수. () -> {}
//                            entity.setTitle(todoDto.getTitle());
//                            entity.setContent(todoDto.getContent());
//                            entity.setDone(todoDto.isDone());
//                            return entity.todoDto();
//                        }
//                )
//                .orElse(null);// findById 결과의 optional 데이터가 존재하지 않으면

    }// f end

    //5. 개별삭제
    public boolean todoDelete(int id){
        //[방법1] 일반적인========================================
        //1. id를 이용하여 엔티티 (존재여부existsById) 조회하기
            // findById 반환타입 : Optional vs existsById 반환타입 : boolean
        boolean result = todoRepository.existsById(id);
        //2. 만약에 존재하면
        if (result == true){
            //3. 영속성제거 , deleteById(pk번호);
            todoRepository.deleteById(id);
            return true; //삭제성공
        }
        return false; // 존재하지 않으면 삭제 취소
        //[방법2] stream========================================
//        return todoRepository.findById(id)
//                .map((entity) -> { // isPresnent 사용도 가능
//                    todoRepository.deleteById(id);
//                    return true;
//                })
//                .orElse(false);
    }

    //6. 전체조회(+ 페이징처리)
    public List<TodoDto> getFindPage(int page,int size){
        // page : 현재 조회중인 페이지 번호
        // size : 페이지 1개 당 조회할 자료 개수
        // 1. PageRequest 클래스
            //PageRequest.of(조회할 페이지번호 , 자료개수 , 정렬[선택])
            // - 조회할페이지번호는 1페이지가 0 부터 시작
            // - 페이지당 조회할 자료 개수
            // - Sort.by(Sort.Direction.ASC, 필드명) : 오름차순
            // - Sort.by(Sort.Direction.DESC, 필드명) : 내림차순(최신순)
        PageRequest pageRequest
                = PageRequest.of(page-1, size , Sort.by(Sort.Direction.DESC,"id"));
        //2. pageRequest객체를 findxxx에 매개변수로 대입한다. . findAll(페이징객체);
        Page<TodoEntity> todoEntityPage = todoRepository.findAll(pageRequest);
            System.out.println("todoEntityPage" + todoEntityPage);
            System.out.println(todoEntityPage.getTotalPages()); // .getToTalPages() : 전체 페이지수 반환
            System.out.println(todoEntityPage.getTotalElements()); // .getTotalElements() : 전체 게시물수 반환
            System.out.println(todoEntityPage.getContent()); // .getContent() : 조회된 자료의 Page타입 --> List타입으로 변환
        //3. page타입의 entity를 dto로 변환
        List<TodoDto> todoDtoList = new ArrayList<>();
        for (int i = 0; i <todoEntityPage.getContent().size();i++){
            TodoDto todoDto = todoEntityPage.getContent().get(i).todoDto();
            todoDtoList.add(todoDto);
        }
        return  todoDtoList;
        //stram
//        return todoRepository.findAll(pageRequest).stream()
//                .map(TodoEntity :: todoDto)
//                .collect(Collectors.toList());
//
    }

    //7. 제목 검색 조회1 (입력한 값이 일치한 제목 조회)
    public List<TodoDto> search1(String title){
       //[1 쿼리메소드]. JPA 리포지토리에서 내가 만든 추상메소드를 사용한다
       return todoRepository.findByTitle(title)
               .stream().map(TodoEntity :: todoDto)
               .collect(Collectors.toList());

       //[2 네이키브 쿼리] JPA 리포지토리에서 내가 만든 추상메소드를 사용한다
//        return todoRepository.findByTitleNative(title)
//                .stream().map(TodoEntity :: todoDto)
//                .collect(Collectors.toList());
    }

    //8. 제목 검색 조회2 (입력한 값이 포함된 제목 조회)

    public List<TodoDto> search2(String title){
        //[1 쿼리메소드] .JPA 리포지토리에서 내가 만든 추상메소드를 사용한다
        return  todoRepository.findByTitleContaining(title)
                .stream().map(TodoEntity :: todoDto)
                .collect(Collectors.toList());
        //[2 쿼리메소드] .JPA 리포지토리에서 내가 만든 추상메소드를 사용한다
//        return todoRepository.findByTitleNativeSearch(title)
//                .stream().map(TodoEntity :: todoDto)
//                .collect(Collectors.toList());
    }



}// class end
