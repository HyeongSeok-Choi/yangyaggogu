package com.mes.yangyaggogu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class equipmentController {


    @GetMapping(value="/equipment")
    public String equipment(Model model) {

        return "equipment/equipment";
    }

}
