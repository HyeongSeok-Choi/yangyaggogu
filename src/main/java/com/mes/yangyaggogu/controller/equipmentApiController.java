package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.CompanyDto;
import com.mes.yangyaggogu.dto.equipmentDTO;
import com.mes.yangyaggogu.service.equipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class equipmentApiController {

    private final equipmentService service;

    @GetMapping("/equipment")
    public Map<String,Object> showEquipments() {

        Map<String,Object> map = new HashMap<String,Object>();

        List<equipmentDTO> companyDtoList = service.findAll().stream()
                .map(a -> new equipmentDTO(a))
                .collect(Collectors.toList());
        map.put("data",companyDtoList);

        System.out.println("데이터테이블 실행 완료");

        return map;
    }


}
