package com.mes.yangyaggogu;

import com.mes.yangyaggogu.constant.equipment_state;
import com.mes.yangyaggogu.constant.workOrderPlan_state;
import com.mes.yangyaggogu.entity.*;
import com.mes.yangyaggogu.repository.*;
import com.mes.yangyaggogu.service.workOrderPlanService;

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
    private final equipmentRepository equipmentRepository;

    public DataLoader(productPlanRepository productPlanRepository,obtainorder_numberRepository obtainOrderNumberRepository,workOrderPlanRepository workOrderPlanRepository,workOrderPlanService workOrderPlanService,employeeRepository employeeRepository,equipmentRepository equipmentRepository) {
        this.workOrderPlanRepository = workOrderPlanRepository;
        this.obtainOrderNumberRepository = obtainOrderNumberRepository;
        this.productPlanRepository = productPlanRepository;
        this.workOrderPlanService = workOrderPlanService;
        this.employeeRepository = employeeRepository;
        this.equipmentRepository = equipmentRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        // Obtain Order 생성
//        obtainorder_number order = new obtainorder_number();
//        order.setOrderNumber("2024-06-21-1");
//        obtainOrderNumberRepository.save(order);
//
//        productPlan plan = new productPlan();
//        plan.setProductionPlanCode("PLAN123");
//        plan.setOrderNumber(order);
//        plan.setPstartDate(LocalDate.now());
//        plan.setMaterialsName("흑마늘즙");
//        plan.setPendDate(LocalDate.now());
//        productPlanRepository.save(plan);
//
//        // Product Plan 생성
//        for (int i = 1; i <= 10; i++) {
//            workOrderPlan workOrder = workOrderPlan.builder()
//                    .processCode("A8")
//                    .productPlanCode(plan)
//                    .obtainorder_number(order)
//                    .producer(null)
//                    .processName("포장")
//                    .P_startDate(LocalDateTime.now().minusDays(i))
//                    .P_endDate(LocalDateTime.now().plusDays(i))
//                    .target_Output(100L)
//                    .now_Output(null)
//                    .state(workOrderPlan_state.completed)
//                    .materials_Name("양배추즙")
//                    .build();
//
//            workOrderPlanService.saveWorkOrderPlan(workOrder);
//        }

            for (int i = 1; i < 5; i++) {
                employee emp = new employee();
                emp.setEmployeeCode("p-"+i);
                emp.setPositionName("생산");

                if(i == 1){
                  emp.setEmployeeName("강두일");
                }else if(i == 2){
                    emp.setEmployeeName("김진영");
                }else if (i == 3) {
                    emp.setEmployeeName("최형석");
                }else{
                    emp.setEmployeeName("손현민");
                }
                employeeRepository.save(emp);

                emp.setEmployeeCode("pl-"+i);
                emp.setPositionName("안전");

                if(i == 1){
                    emp.setEmployeeName("최돌석");
                }else if(i == 2){
                    emp.setEmployeeName("강두식");
                }else if (i == 3) {
                    emp.setEmployeeName("김진숙");
                }else{
                    emp.setEmployeeName("손현식");
                }
                employeeRepository.save(emp);

                emp.setEmployeeCode("ad-"+i);
                emp.setPositionName("행정");

                if(i == 1){
                    emp.setEmployeeName("장이수");
                }else if(i == 2){
                    emp.setEmployeeName("김상철");
                }else if (i == 3) {
                    emp.setEmployeeName("마춘식");
                }else{
                    emp.setEmployeeName("장첸");
                }
                employeeRepository.save(emp);


            }




        equipment equip = new equipment();
        for (int i = 1; i < 3; i++) {

            equip.setEquipmentCode("JW" + i);
            equip.setSettingDate(LocalDate.now().minusYears(10));
            equip.setEquipmentName("즙 포장기");
            equip.setOperationRate(0L);
            equip.setState(equipment_state.run);
            equip.setReasonNoOp("-");
            equipmentRepository.save(equip);

            equip.setEquipmentCode("SW" + i);
            equip.setEquipmentName("스틱 포장기");
            equip.setSettingDate(LocalDate.now().minusYears(8).minusMonths(1).minusDays(14));
            equipmentRepository.save(equip);

            equip.setEquipmentCode("JC" + i);
            equip.setEquipmentName("착즙기");
            equip.setSettingDate(LocalDate.now().minusYears(14).minusDays(7));
            equipmentRepository.save(equip);

            equip.setEquipmentCode("SZ" + i);
            equip.setEquipmentName("살균기");
            equip.setSettingDate(LocalDate.now().minusYears(8).minusMonths(4).minusDays(2));
            equipmentRepository.save(equip);

            equip.setEquipmentCode("MX");
            equip.setEquipmentName("혼합기");
            equip.setSettingDate(LocalDate.now().minusYears(14).minusDays(14));
            equipmentRepository.save(equip);

            equip.setEquipmentCode("PC");
            equip.setEquipmentName("여과기");
            equip.setSettingDate(LocalDate.now().minusYears(18).minusMonths(3).minusDays(5));
            equipmentRepository.save(equip);

            equip.setEquipmentCode("BX");
            equip.setEquipmentName("박스 포장기");
            equip.setSettingDate(LocalDate.now().minusYears(16).minusDays(6));
            equipmentRepository.save(equip);

            equip.setEquipmentCode("MD");
            equip.setEquipmentName("금속검출기");
            equip.setSettingDate(LocalDate.now().minusYears(6).minusMonths(7).minusDays(7));
            equipmentRepository.save(equip);




        }




    }
}
