package example.day04_과제.controller;


import example.day04.model.dto.TodoDto;
import example.day04_과제.model.dto.EquipmentDto;
import example.day04_과제.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day04/equip")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EquipmentController {

    private final EquipmentService equipmentService;

    //1. 등록 {"name" : "비품1" , "description" : "비상보충용" , "quantity" : "10"}
    @PostMapping
    public EquipmentDto EqupSave(@RequestBody EquipmentDto equipmentDto){
        return equipmentService.EqupSave(equipmentDto);
    }

    //2. 전체조회
    @GetMapping
    public List<EquipmentDto> equipFindAll(){
        return equipmentService.equipFindAll();
    }

    //3. 개별조회
    @GetMapping("/view")
    public EquipmentDto equipFindById(@RequestParam int id){
        return equipmentService.equipFindById(id);
    }

    //4. 개별수정
    @PutMapping
    public EquipmentDto equipUpdate(@RequestBody EquipmentDto equipDto){
        return equipmentService.equipUpdate(equipDto);
    }

    //5. 개별삭제
    @DeleteMapping
    public boolean equipDelete(@RequestParam int id){
        return equipmentService.equipDelete(id);
    }

    //6. 전체조회
    @GetMapping("/page")
    public List<EquipmentDto> getFindPage(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "3") int size){
        return equipmentService.getFindPage(page,size);
    }

    //7. 제목검색 조회
    @GetMapping("/search1")
    public List<TodoDto> search(@RequestParam String name){
        return equipmentService.search(name);
    }

    @GetMapping("/search2")
    public List<TodoDto> search2(@RequestParam String keyword){
        return equipmentService.search2(keyword);
    }


}
