package com.mes.yangyaggogu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RefereceInfoController {

    @GetMapping("/reference_em_info")
    public String getEmployeeInfo() {
        return "ReferenceInfo/EmployeeInfo";
    }
}
