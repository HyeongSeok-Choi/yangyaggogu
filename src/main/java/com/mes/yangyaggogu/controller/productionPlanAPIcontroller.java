package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.service.productPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class productionPlanAPIcontroller {

    private final productPlanService P_planService;

    //생산계획표 모두 출력
    @GetMapping(value = "/getProductionPlanList")
    public Map<String, Object> productionPlanList() {

        //datatable 사용시 data를 키로 가져야 해서 넣음
        Map<String, Object> P_plans = new HashMap<String, Object>();

        P_plans.put("data",P_planService.getProductPlans());

        return P_plans;
    }
}
