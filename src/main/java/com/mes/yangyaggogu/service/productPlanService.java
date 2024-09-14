package com.mes.yangyaggogu.service;


import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import com.mes.yangyaggogu.repository.productPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class productPlanService {


    private final  productPlanRepository P_planRepository;
    private final obtainorder_detailRepository obtainorder_detailRepository;
    private final obtainorder_numberRepository obtainorder_NumberRepository;

    //생산 계획 모두 출력
    public List<productPlan> getProductPlans() {

        return P_planRepository.findAll();
    }

    //생산 계획 얻기
    public productPlan getProductPlan(String productPlanId) {

       productPlan productPlan=  P_planRepository.findById(productPlanId).orElseThrow();


        return productPlan;
    }

    //오늘 생산계획 모두 출력
    public List<productPlan> getProductPlansToday() {

        return P_planRepository.getTodayPlan(LocalDate.now());
    }


    //생산 시작전 계획 모두 출력
    public List<productPlan> getProductPlansBeforeOrder() {


       List<productPlan> BeforeOrderList = P_planRepository.findAllByState(productionPlan_state.beforeOrder);

//       List<productPlan> FilterList = new ArrayList<>();
//
//       LocalDate lowStockRegisterDate=null;
//
//        for (productPlan productPlan : BeforeOrderList) {
//
//            String[] orderNum =  productPlan.getProductionPlanCode().split(",");
//
//            System.out.println(orderNum[0].substring(0,orderNum[0].length() - 2)+"매그네릭");
//            obtainorder_number findObtainNum =obtainorder_NumberRepository.findById(orderNum[0].substring(0,orderNum[0].length() - 2)).orElseThrow();
//
//            List<obtainorder_detail> obtainorderDetailList = obtainorder_detailRepository.findByOrderNumber(findObtainNum);
//
//
//            LocalDate deliveryDate1 = null;
//            for (obtainorder_detail obtainorderDetail:obtainorderDetailList){
//                deliveryDate1= obtainorderDetail.getDelivery_Date();
//            }
//
//
//            LocalDate deliveryDate = deliveryDate1;
//
//
//            if (productPlan.getMaterialsName().equals("양배추즙") || productPlan.getMaterialsName().equals("흑마늘즙")) {
//                lowStockRegisterDate = deliveryDate.minusDays(8);
//                System.out.println(lowStockRegisterDate);
//
//
//                if (lowStockRegisterDate.getDayOfWeek().getValue() == 6 || lowStockRegisterDate.getDayOfWeek().getValue() == 7) {
//                    lowStockRegisterDate = lowStockRegisterDate.minusDays(2);
//                }
//
//            } else {
//                lowStockRegisterDate = deliveryDate.minusDays(9);
//                System.out.println(lowStockRegisterDate);
//
//                if (lowStockRegisterDate.getDayOfWeek().getValue() == 6 || lowStockRegisterDate.getDayOfWeek().getValue() == 7) {
//                    lowStockRegisterDate = lowStockRegisterDate.minusDays(2);
//                }
//
//            }
//
//            if(lowStockRegisterDate.isEqual(LocalDate.now())){
//
//                FilterList.add(productPlan);
//            }
//        }


        return  BeforeOrderList;
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
