package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.entity.company;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.service.CompanyService;
import com.mes.yangyaggogu.service.shipmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@AllArgsConstructor
@Controller
public class shipmentController {


    final private shipmentService shipmentService;
    final private CompanyService companyService;
    @GetMapping("shipment/list")
    public String showShipmentRegisterForm()  {
        return "shipment/shipmentRegister";
    }



    @GetMapping("/shipment/confirmedList")
    public String showShipmentList() {
        return "shipment/shipmentList";
    }


    @GetMapping("/shipment/{id}")
    public String getShipment(@PathVariable String id, Model model) {

        shipment shipment = shipmentService.findById(id);
        model.addAttribute("shipment", shipment);



        return "shipment/shipmentDetailRegister";
    }




}
