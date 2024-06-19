package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.dto.P_plan_calendarDTO;
import com.mes.yangyaggogu.dto.productPlanDTO;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.service.productPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<productPlanDTO> productPlanDTOList = P_planService.getProductPlans().stream()
                        .map(a -> new productPlanDTO(a))
                                .collect(Collectors.toList());


        P_plans.put("data",productPlanDTOList);

        return P_plans;
    }

    @GetMapping(value = "/getProductionPlanListCalendar")
    public List<P_plan_calendarDTO> productionPlanListCalendar() {

        List<P_plan_calendarDTO> prdList = new ArrayList<>();

        for (productPlan prd_p :P_planService.getProductPlans()){

            System.out.println(prd_p.getP_startDate()+"이거");
            P_plan_calendarDTO pPlanCalendarDTO=new P_plan_calendarDTO();

            pPlanCalendarDTO.setStart(prd_p.getP_startDate().toString());

            pPlanCalendarDTO.setEnd(prd_p.getP_endDate().toString());

            pPlanCalendarDTO.setTitle(prd_p.getProductionPlanCode());

            prdList.add(pPlanCalendarDTO);

        }

        return prdList;
    }


}
