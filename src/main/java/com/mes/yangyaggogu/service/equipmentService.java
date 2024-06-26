package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.entity.equipment;
import com.mes.yangyaggogu.repository.equipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class equipmentService {

    private final equipmentRepository equipmentRepository;


    //모든 설비현황 출력
    public List<equipment> findAll() {
        return equipmentRepository.findAll();
    }

}
