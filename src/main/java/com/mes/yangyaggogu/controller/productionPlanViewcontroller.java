package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.service.productPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class productionPlanViewcontroller {

    private final productPlanService productPlanService;

    @GetMapping(value = "productionPlan_calendar")
    public String productionPlan_calendar() {

        return "productionPlan/productionPlan_calendar";

    }

    @GetMapping(value = "productionPlan_table")
    public String productionPlan_table() {

       // productPlanService.addProductPlan();
        return "productionPlan/productionPlan_Table";

    }

}
