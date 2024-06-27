package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.workOrderPlan_state;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.entity.workPerform;
import com.mes.yangyaggogu.repository.workOrderPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkPerformService {

    private final workOrderPlanRepository workOrderPlanRepository;


    private final productPlanService productPlanService;

    public void MakeWorkPerform() {
       List<productPlan> productPlanList = productPlanService.getProductPlansToday();

       for (productPlan productPlan : productPlanList) {
         List<workOrderPlan> workOrderPlanList=  productPlan.getWorkOrderPlanList();
         for (workOrderPlan workOrderPlan : workOrderPlanList) {
             if(workOrderPlan.getP_startDate() != null && workOrderPlan.getState() == workOrderPlan_state.proceeding){

                 LocalDateTime startTime = workOrderPlan.getP_startDate();
                 LocalDateTime endTime = LocalDateTime.now();

                 Duration du = Duration.between(startTime, endTime);

                 float calculatorRate = ((float) du.getSeconds()/60/120*100);


                 switch (workOrderPlan.getProcessCode()){
                     case "A1":
                         calculatorRate =((float) du.getSeconds()/60/120*100);
                         break;
                     case "A2":
                         calculatorRate =((float) du.getSeconds()/60/120*100);
                         break;
                     case "A3":
                         calculatorRate =((float) du.getSeconds()/60/1440*100);
                         break;

                     case "A4":
                         calculatorRate =((float) du.getSeconds()/60/240*100);
                         break;
                     case "A5":
                         calculatorRate =((float) du.getSeconds()/60/120*100);
                         break;
                     case "A6":
                         calculatorRate =((float) du.getSeconds()/60/480*100);
                         break;
                     case "A7":
                         calculatorRate =((float) du.getSeconds()/60/120*100);
                         break;

                     case "A8":
                         calculatorRate =((float) du.getSeconds()/60/120*100);
                         break;

                     case "B1":
                         calculatorRate =((float) du.getSeconds()/60/60*100);
                         break;
                     case "B2":
                         calculatorRate =((float) du.getSeconds()/60/480*100);
                         break;
                     case "B3":
                         calculatorRate =((float) du.getSeconds()/60/120*100);
                         break;
                     case "B4":
                         calculatorRate =((float) du.getSeconds()/60/120*100);
                         break;
                     case "B5":
                         calculatorRate =((float) du.getSeconds()/60/480*100);
                         break;
                     case "B6":
                         calculatorRate =((float) du.getSeconds()/60/60*100);
                         break;
                     case "B7":
                         calculatorRate =((float) du.getSeconds()/60/60*100);
                         break;

                     default:
                         break;
                 }


                 if(calculatorRate>=100){
                     calculatorRate=100;
                 }

                 workOrderPlan.setOperationRate(calculatorRate);

                 workOrderPlanRepository.save(workOrderPlan);
             }

         }

       }

    }



}
