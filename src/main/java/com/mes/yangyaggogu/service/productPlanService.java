package com.mes.yangyaggogu.service;


import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import com.mes.yangyaggogu.repository.productPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class productPlanService {


    private final  productPlanRepository P_planRepository;
    private final obtainorder_numberRepository o_planRepository;

    public List<productPlan> getProductPlans() {

        return P_planRepository.findAll();
    }


    public void addProductPlan() {

        for (int i = 0; i < 50; i++) {

            productPlan productPlan = new productPlan();


            productPlan.setProductionPlanCode("p-100"+i);
            productPlan.setState(productionPlan_state.ready);
            productPlan.setP_startDate(LocalDateTime.now());
            productPlan.setP_endDate(LocalDateTime.now().plusDays(i));
            productPlan.setTarget_Output(500L);
            obtainorder_number obtainorder_number = new obtainorder_number();
            obtainorder_number.setOrder_Number("a-500"+i);
            o_planRepository.save(obtainorder_number);


            productPlan.setOrder_Number(obtainorder_number);

            P_planRepository.save(productPlan);

        }





    }

}
