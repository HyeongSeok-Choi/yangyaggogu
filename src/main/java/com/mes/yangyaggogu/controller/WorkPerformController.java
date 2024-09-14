package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.dto.productPlanDTO;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.entity.workPerform;
import com.mes.yangyaggogu.service.WorkPerformService;
import com.mes.yangyaggogu.service.productPlanService;
import com.mes.yangyaggogu.service.workOrderPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class WorkPerformController {

    private final WorkPerformService workPerformService;
    private final workOrderPlanService workPlanService;
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

    @PostMapping(value = "/getProcessOperate")
    public ResponseEntity<Map<String,ArrayList<Float> >> getProcessOperate(@RequestBody productPlanDTO productionPlanCode) {


        productPlan productPlan1 = productPlanService.getProductPlan(productionPlanCode.getProductionPlanCode());

//        Map<String,Map<String,Float>> data = new HashMap<>();

        Map<String,ArrayList<Float> > data = new HashMap<>();

        workPlanService.getByProductPlanCode(productPlan1);


            data.put("data",workPlanService.getByProductPlanCode(productPlan1));


        System.out.println(workPlanService.getByProductPlanCode(productPlan1));

        return ResponseEntity.ok().body(data);


    }


}
