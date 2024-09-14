package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.entity.equipment;
import com.mes.yangyaggogu.repository.equipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class equipmentService {

    private final equipmentRepository equipmentRepository;

    
    //설비 등록
    public equipment addEquipment(equipment equipment) throws Exception {

        if(equipmentRepository.existsById(equipment.getEquipmentCode())){
                    return null;
        }


        return  equipmentRepository.save(equipment);
    }

    //설비 삭제
    public void deleteEquipment(List<String> Codes) {

       equipmentRepository.deleteAllById(Codes);
    }


    //모든 설비현황 출력
    public List<equipment> findAll() {
        return equipmentRepository.findAll();
    }

}
