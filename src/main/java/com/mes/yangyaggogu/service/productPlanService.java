package com.mes.yangyaggogu.service;


import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import com.mes.yangyaggogu.repository.productPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class productPlanService {


    private final  productPlanRepository P_planRepository;
    private final obtainorder_numberRepository o_planRepository;

    //생산 계획 모두 출력
    public List<productPlan> getProductPlans() {

        return P_planRepository.findAll();
    }

    //생산 계획 얻기
    public productPlan getProductPlan(String productPlanId) {

       productPlan productPlan=  P_planRepository.findById(productPlanId).orElseThrow();


        return productPlan;
    }


    //생산 시작적 계획 모두 출력
    public List<productPlan> getProductPlansBeforeOrder() {

        return P_planRepository.findAllByState(productionPlan_state.beforeOrder);
    }
    
    //저장
    public productPlan save(productPlan productPlan) {
        return P_planRepository.save(productPlan);
    }

//    public boolean calculatorCapacity(obtainorder_detail findObtain) {
//
//        //제품명
//        String productName = findObtain.getProductName();
//
//        //제품명이 양배추즙 또는 흑마늘즙일때
//        if(productName.equals("양배추즙")||productName.equals("흑마늘즙")){
//
//
//            //캡파가 250box (1000kg)를 넘는다면 두라인으로 잡아야함
//            if(findObtain.getOrder_Amount() > 250){
//
//
//            }
//        }else {
//
//
//        }
//
//
//    }

}
