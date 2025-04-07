package example.day04_과제.service;

import example.day04.model.dto.TodoDto;
import example.day04_과제.model.dto.EquipmentDto;
import example.day04_과제.model.entity.EquipmentEntity;
import example.day04_과제.model.repository.EquipmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    //1. 등록
    @PostMapping
    public EquipmentDto EqupSave(@RequestBody EquipmentDto equipmentDto){
        EquipmentEntity equipmentEntity = equipmentDto.toEquipmentEntity();
        EquipmentEntity save = equipmentRepository.save(equipmentEntity);
        if (save.getId() > 1) {return save.toDto();}
        else {return null;}
    }

    //2. 전체조회
    @GetMapping
    public List<EquipmentDto> equipFindAll(){
        return equipmentRepository.findAll().stream().map(EquipmentEntity :: toDto).collect(Collectors.toList());
    }

    //3. 개별조회
    @GetMapping("/view")
    public EquipmentDto equipFindById(@RequestParam int id){
        return equipmentRepository.findById(id)
                .map(EquipmentEntity :: toDto)
                .orElse(null);

    }

    //4. 개별수정
    @PutMapping
    public EquipmentDto equipUpdate(@RequestBody EquipmentDto equipDto){
        return equipmentRepository.findById(equipDto.getId())
                .map(entity ->{
                    entity.setId(equipDto.getId());
                    entity.setName(equipDto.getName());
                    entity.setDescription(equipDto.getDescription());
                    entity.setQuantity(equipDto.getQuantity());
                    return entity.toDto();
                })
                .orElse(null);

    }

    //5. 개별삭제
    @DeleteMapping
    public boolean equipDelete(@RequestParam int id){
        return  equipmentRepository.findById(id)
                .map((entity) -> {
                    equipmentRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    //6. 전체조회
    @GetMapping("/Page")
    public List<EquipmentDto> getFindPage(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "3") int size){
        PageRequest pageRequest =
                PageRequest.of(page-1, size);


        return  equipmentRepository.findAll(pageRequest).stream()
                .map(EquipmentEntity :: toDto)
                .collect(Collectors.toList());
    }

    //7. 제목검색 조회

    public List<TodoDto> search( String name){
        return null;
    }


    public List<TodoDto> search2( String keyword){
        return null;
    }



}
