package com.mes.yangyaggogu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testcontroller {

    @GetMapping(value = "/")
    public String test() {
        return "index";
    }

    @GetMapping(value = "/test")
    public String test1() {
        return "test";
    }
}
