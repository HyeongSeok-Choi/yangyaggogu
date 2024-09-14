package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.entity.employee;
import com.mes.yangyaggogu.service.employeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefereceInfoController {

    private final employeeService empService;

    @GetMapping("/reference_em_info")
    public String getEmployeeInfo(Model model) {

        List<employee> employeeList= empService.getEmployee();

        model.addAttribute("employeeList",employeeList);


        return "ReferenceInfo/EmployeeInfo";
    }

    @GetMapping("/reference_em_info_EmpConfirm")
    public String EmployeeConfirm() {

        return "ReferenceInfo/employeeRegister";
    }
}
