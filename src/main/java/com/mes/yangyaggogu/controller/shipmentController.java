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
import java.util.Optional;

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

        String companyName = shipment.getCompany_name();
        Optional<company> company = companyService.findByCompanyName(companyName);

        // company 데이터를 모델에 추가합니다.
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
        } else {
            model.addAttribute("company", new company()); // 기본 빈 객체를 추가하여 NPE 방지
        }
        return "shipment/shipmentDetailRegister";
    }




}
