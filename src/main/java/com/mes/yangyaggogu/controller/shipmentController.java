package com.mes.yangyaggogu.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@AllArgsConstructor
@Controller
public class shipmentController {

    @GetMapping("shipment/list")
    public String showShipmentRegisterForm()  {
        return "shipment/shipmentRegister";
    }



    @GetMapping("/shipment/confirmedList")
    public String showShipmentList() {
        return "shipment/shipmentList";
    }



}
