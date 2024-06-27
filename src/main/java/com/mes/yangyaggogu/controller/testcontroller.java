package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.service.productPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class testcontroller {

    private final productPlanService productPlanService;



    @GetMapping(value = "/")
    public String test(Model model) {

      model.addAttribute("plans",productPlanService.getProductPlansToday());

        return "index";
    }

    @GetMapping(value = "/test")
    public String test1() {
        return "test";
    }
}
