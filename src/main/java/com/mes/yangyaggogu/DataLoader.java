package com.mes.yangyaggogu;

import com.mes.yangyaggogu.constant.workOrderPlan_state;
import com.mes.yangyaggogu.entity.employee;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.repository.employeeRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import com.mes.yangyaggogu.repository.workOrderPlanRepository;
import com.mes.yangyaggogu.service.workOrderPlanService;
import com.mes.yangyaggogu.entity.obtainorder_number;

import com.mes.yangyaggogu.repository.productPlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final com.mes.yangyaggogu.repository.workOrderPlanRepository workOrderPlanRepository;
    private final obtainorder_numberRepository obtainOrderNumberRepository;
    private final productPlanRepository productPlanRepository;
    private final workOrderPlanService workOrderPlanService;
    private final employeeRepository employeeRepository;

    public DataLoader(productPlanRepository productPlanRepository,obtainorder_numberRepository obtainOrderNumberRepository,workOrderPlanRepository workOrderPlanRepository,workOrderPlanService workOrderPlanService,employeeRepository employeeRepository) {
        this.workOrderPlanRepository = workOrderPlanRepository;
        this.obtainOrderNumberRepository = obtainOrderNumberRepository;
        this.productPlanRepository = productPlanRepository;
        this.workOrderPlanService = workOrderPlanService;
        this.employeeRepository = employeeRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        // Obtain Order 생성
        obtainorder_number order = new obtainorder_number();
        order.setOrder_Number("20240615-001");
        obtainOrderNumberRepository.save(order);

        productPlan plan = new productPlan();
        plan.setProductionPlanCode("PLAN123");
        plan.setOrder_Number(order);
        plan.setPstartDate(LocalDate.now());
        plan.setMaterials_Name("흑마늘즙");
        plan.setPendDate(LocalDate.now());
        productPlanRepository.save(plan);

        // Product Plan 생성
        for (int i = 1; i <= 10; i++) {
            workOrderPlan workOrder = workOrderPlan.builder()
                    .processCode("A8")
                    .productPlanCode(plan)
                    .obtainorder_number(order)
                    .producer(null)
                    .processName("포장" )
                    .P_startDate(LocalDateTime.now().minusDays(i))
                    .P_endDate(LocalDateTime.now().plusDays(i))
                    .target_Output(100L)
                    .now_Output(null)
                    .state(workOrderPlan_state.completed)
                    .materials_Name("양배추즙")
                    .build();

            workOrderPlanService.saveWorkOrderPlan(workOrder);
        }

            for (int i = 0; i < 10; i++) {
                employee emp = new employee();
                emp.setEmployeeCode("p-"+i);
                emp.setEmployeeName("안드로이드"+i);
                emp.setPositionName("생산자");
                employeeRepository.save(emp);

            }

    }
}
