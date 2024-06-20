package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.dto.P_plan_calendarDTO;
import com.mes.yangyaggogu.dto.productPlanDTO;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.service.productPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
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

            P_plan_calendarDTO pPlanCalendarDTO=new P_plan_calendarDTO();

            String str =prd_p.getMaterials_Name();

            switch (str){
                    case "양배추즙":
                    pPlanCalendarDTO.setColor("#66ff66");
                    break;
                     case "흑마늘즙":
                    pPlanCalendarDTO.setColor("#000000");
                    break;

                    case "매실젤리":
                    pPlanCalendarDTO.setColor("#669900");
                    break;

                    case "석류젤리":
                    pPlanCalendarDTO.setColor("#ff0066");
                    break;
                default:
                    pPlanCalendarDTO.setColor("알수없음");
                    break;
            }

            pPlanCalendarDTO.setStart(prd_p.getPstartDate());

            pPlanCalendarDTO.setEnd(prd_p.getPendDate().plusDays(1));

            pPlanCalendarDTO.setTitle(prd_p.getProductionPlanCode()+" "+prd_p.getMaterials_Name()+" : "+prd_p.getTarget_Output()+" box");

            prdList.add(pPlanCalendarDTO);

        }

        return prdList;
    }


}
