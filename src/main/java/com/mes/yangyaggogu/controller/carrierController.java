package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.entity.carrier;
import com.mes.yangyaggogu.entity.company;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.service.carrierService;
import com.mes.yangyaggogu.service.shipmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class carrierController {

    final private carrierService carrierService;
    final private shipmentService shipmentService;




    @PostMapping("/carrier/register")
    //운송업체 데이터베이스 저장 컨트롤러

    public String registerCompany(@ModelAttribute carrier carrier,
                                  @RequestParam String shipment_Number,
                                  RedirectAttributes redirectAttributes) {


        shipment shipment = shipmentService.findById(shipment_Number);

        carrier.setShipment(shipment);


        carrier existingCarrier = carrierService.findByShipment(shipment);

        if (existingCarrier != null) {
            // Update existing carrier
            existingCarrier.setCarrier_Name(carrier.getCarrier_Name());
            existingCarrier.setCarrier_PhoneNumber(carrier.getCarrier_PhoneNumber());
            existingCarrier.setCarrier_Vehicle(carrier.getCarrier_Vehicle());
            existingCarrier.setCarrier_Price(carrier.getCarrier_Price());
            existingCarrier.setCarrier_manager(carrier.getCarrier_manager());
            carrierService.registerCarrier(existingCarrier);
        } else {
            // Save new carrier
            carrierService.registerCarrier(carrier);
        }



        return "shipment/shipmentList";
    }






}
