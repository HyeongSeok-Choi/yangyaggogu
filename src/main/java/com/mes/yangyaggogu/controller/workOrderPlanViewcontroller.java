package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.company_state;
import com.mes.yangyaggogu.entity.employee;
import com.mes.yangyaggogu.service.employeeService;
import com.mes.yangyaggogu.service.productPlanService;
import com.mes.yangyaggogu.service.workOrderPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class workOrderPlanViewcontroller {


         private final workOrderPlanService workPlanService;
         private final employeeService empService;
//
        @GetMapping(value = "/workOrder")
        public String workOrderPlanView(Model model){



            List<employee> producers = empService.getProductor();

            for (employee employee : producers) {
                System.out.println(employee.getEmployeeName());
            }

            model.addAttribute("producers", producers);

           //workPlanService.testWorkOrderPlanData();

            return "WorkOrder/WorkOrder";
        }


}
