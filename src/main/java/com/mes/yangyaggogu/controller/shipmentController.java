package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.constant.shipment_state;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.carrier;
import com.mes.yangyaggogu.entity.company;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.service.CompanyService;
import com.mes.yangyaggogu.service.carrierService;
import com.mes.yangyaggogu.service.shipmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class shipmentController {

    final private carrierService carrierService;
    final private shipmentService shipmentService;
    final private CompanyService companyService;
    @GetMapping("shipment/list")
    public String showShipmentRegisterForm(Model model, searchDto searchDto)  {

        model.addAttribute("searchDto", searchDto);
        return "shipment/shipmentRegister";
    }



    @GetMapping("/shipment/confirmedList")
    public String showShipmentList(Model model, searchDto searchDto) {
        model.addAttribute("searchDto", searchDto);
        return "shipment/shipmentList";
    }



@GetMapping("/shipment/confirmedList/{id}")
public String getShipment(@PathVariable String id, @RequestParam(required = false) shipment_state status, Model model) {
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

    carrier carrier = carrierService.findByShipment(shipment);
    if (carrier == null) {
        carrier = new carrier(); // 운송업체 정보 기입한적이 없으면 빈칸 출력.
    }
    model.addAttribute("carrier", carrier);

    // 상태를 모델에 추가합니다.
    if (status == shipment_state.completed) {
        model.addAttribute("status", "completed");
    } else {
        model.addAttribute("status", "new");
    }

    return "shipment/shipmentDetailRegister";
}



}
