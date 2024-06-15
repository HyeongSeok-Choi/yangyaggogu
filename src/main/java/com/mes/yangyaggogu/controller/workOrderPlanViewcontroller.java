package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.service.productPlanService;
import com.mes.yangyaggogu.service.workOrderPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class workOrderPlanViewcontroller {


         private final workOrderPlanService workPlanService;

        @GetMapping(value = "/workOrder")
        public String workOrderPlanView(){

            //workPlanService.testWorkOrderPlanData();

            return "WorkOrder/WorkOrder";
        }

}
