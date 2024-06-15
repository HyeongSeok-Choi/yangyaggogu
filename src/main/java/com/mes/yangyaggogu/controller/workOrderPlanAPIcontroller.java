package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.dto.productPlanDTO;
import com.mes.yangyaggogu.dto.workOrderPlanDTO;
import com.mes.yangyaggogu.service.productPlanService;
import com.mes.yangyaggogu.service.workOrderPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class workOrderPlanAPIcontroller {

    private final workOrderPlanService workOrderPlanService;

    //생산계획표 모두 출력
    @GetMapping(value = "/getWorkOrderPlanList")
    public Map<String, Object> workOrderPlanList() {

        //datatable 사용시 data를 키로 가져야 해서 넣음
        Map<String, Object> w_plans = new HashMap<String, Object>();

        List<workOrderPlanDTO> workOrderPlanDTOList = workOrderPlanService.getAll().stream()
                .map(a -> new workOrderPlanDTO(a))
                .collect(Collectors.toList());


        w_plans.put("data",workOrderPlanDTOList);

        return w_plans;
    }



}
