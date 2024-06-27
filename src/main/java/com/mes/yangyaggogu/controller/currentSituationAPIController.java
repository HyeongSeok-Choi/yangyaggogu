package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.equipmentDTO;
import com.mes.yangyaggogu.dto.workOrderPlanDTO;
import com.mes.yangyaggogu.service.equipmentService;
import com.mes.yangyaggogu.service.workOrderPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class currentSituationAPIController {

    private final workOrderPlanService workOrderPlanService;

    @GetMapping("/todayProductAmount")
    public ResponseEntity<?> showEquipments() {

//        List<workOrderPlanDTO> workOrderPlanDTOList = workOrderPlanService.getAllToday().stream()
//                .map(a -> new workOrderPlanDTO(a))
//                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();

        data.put("data",workOrderPlanService.getAllToday());



        return ResponseEntity.ok().body(data);
    }



}
