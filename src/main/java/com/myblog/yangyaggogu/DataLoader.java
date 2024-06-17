package com.myblog.yangyaggogu;

import com.myblog.yangyaggogu.constant.workOrderPlan_state;
import com.myblog.yangyaggogu.entity.obtainorder_number;
import com.myblog.yangyaggogu.entity.productPlan;
import com.myblog.yangyaggogu.constant.productionPlan_state;

import com.myblog.yangyaggogu.entity.workOrderPlan;
import com.myblog.yangyaggogu.repository.obtainorder_numberRepository;
import com.myblog.yangyaggogu.repository.productPlanRepository;
import com.myblog.yangyaggogu.repository.workOrderPlanRepository;
import com.myblog.yangyaggogu.service.workOrderPlanService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final workOrderPlanRepository workOrderPlanRepository;
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
        order.setOrder_Number("ORDER123");
        obtainOrderNumberRepository.save(order);

        productPlan plan = new productPlan();
        plan.setProductionPlanCode("PLAN123");
        productPlanRepository.save(plan);

        // Product Plan 생성
        for (int i = 1; i <= 10; i++) {
            workOrderPlan workOrder = workOrderPlan.builder()
                    .processCode("PROCESS" + i)
                    .productPlanCode(plan)
                    .obtainorder_number(order)
                    .producer("Producer " + i)
                    .processName("포장" )
                    .P_startDate(LocalDateTime.now().minusDays(i))
                    .P_endDate(LocalDateTime.now().plusDays(i))
                    .target_Output(100L + i)
                    .now_Output(50L + i)
                    .state(workOrderPlan_state.completed)
                    .materials_Name("Material " + i)
                    .build();

            workOrderPlanService.saveWorkOrderPlan(workOrder);
        }
    }
}
