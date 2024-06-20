package com.mes.yangyaggogu;

import com.mes.yangyaggogu.constant.workOrderPlan_state;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import com.mes.yangyaggogu.repository.workOrderPlanRepository;
import com.mes.yangyaggogu.service.workOrderPlanService;
import com.mes.yangyaggogu.entity.obtainorder_number;

import com.mes.yangyaggogu.repository.productPlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final com.mes.yangyaggogu.repository.workOrderPlanRepository workOrderPlanRepository;
    private final obtainorder_numberRepository obtainOrderNumberRepository;
    private final productPlanRepository productPlanRepository;
    private final workOrderPlanService workOrderPlanService;

    public DataLoader(productPlanRepository productPlanRepository,obtainorder_numberRepository obtainOrderNumberRepository,workOrderPlanRepository workOrderPlanRepository,workOrderPlanService workOrderPlanService) {
        this.workOrderPlanRepository = workOrderPlanRepository;
        this.obtainOrderNumberRepository = obtainOrderNumberRepository;
        this.productPlanRepository = productPlanRepository;
        this.workOrderPlanService = workOrderPlanService;

    }

    @Override
    public void run(String... args) throws Exception {
        // Obtain Order 생성
        obtainorder_number order = new obtainorder_number();
        order.setOrder_Number("2024-06-20-1");
        obtainOrderNumberRepository.save(order);

        productPlan plan = new productPlan();
        plan.setProductionPlanCode("2024-06-19T09:34:45.192947400prP-001");
        productPlanRepository.save(plan);

        // Product Plan 생성
        for (int i = 1; i <= 10; i++) {
            workOrderPlan workOrder = workOrderPlan.builder()
                    .processCode("A8")
                    .productPlanCode(plan)
                    .obtainorder_number(order)
                    .producer(null)
                    .processName("포장")
                    .P_startDate(LocalDateTime.now().minusDays(i))
                    .P_endDate(LocalDateTime.now().plusDays(i))
                    .target_Output(100L)
                    .now_Output(null)
                    .state(workOrderPlan_state.completed)
                    .materials_Name("양배추즙")
                    .build();

            workOrderPlanService.saveWorkOrderPlan(workOrder);
        }
    }
}
