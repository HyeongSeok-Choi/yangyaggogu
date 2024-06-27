package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.entity.workPerform;
import com.mes.yangyaggogu.service.WorkPerformService;
import com.mes.yangyaggogu.service.productPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class WorkPerformController {

    private final WorkPerformService workPerformService;
    private final productPlanService productPlanService;

    @PostMapping(value = "/updateOperationRate")
    public ResponseEntity<?> updateWorkPerform() {

        workPerformService.MakeWorkPerform();

       List<productPlan> productPlanList = productPlanService.getProductPlansToday();

       for (productPlan productPlan:productPlanList){

           List<workOrderPlan> workPerformList= productPlan.getWorkOrderPlanList();

           for(workOrderPlan workPerform:workPerformList){
               workPerform.setOperationRate(workPerform.getOperationRate()+1);
           }

       }
       return ResponseEntity.ok().body(null);


    }


}
