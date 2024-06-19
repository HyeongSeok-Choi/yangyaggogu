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




    //생산계획 50개의 더미 데이터 렛츠 기릿
    public void addProductPlan() {

        for (int i = 0; i < 50; i++) {

            productPlan productPlan = new productPlan();


            productPlan.setProductionPlanCode("p-100"+i);
            productPlan.setState(productionPlan_state.ready);
            productPlan.setPstartDate(LocalDate.now());
            productPlan.setMaterials_Name("양배추즙");
            productPlan.setPendDate(LocalDate.now().plusDays(i));
            productPlan.setTarget_Output(500L);
            obtainorder_number obtainorder_number = new obtainorder_number();
            obtainorder_number.setOrder_Number("a-500"+i);
            o_planRepository.save(obtainorder_number);


            productPlan.setOrder_Number(obtainorder_number);

            P_planRepository.save(productPlan);

        }





    }

}
