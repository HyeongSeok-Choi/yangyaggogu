package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.shipment_state;
import com.mes.yangyaggogu.entity.carrier;
import com.mes.yangyaggogu.entity.company;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.service.carrierService;
import com.mes.yangyaggogu.service.shipmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

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

            existingCarrier.setCarrier_Name(carrier.getCarrier_Name());
            existingCarrier.setCarrier_PhoneNumber(carrier.getCarrier_PhoneNumber());
            existingCarrier.setCarrier_Vehicle(carrier.getCarrier_Vehicle());
            existingCarrier.setCarrier_Price(carrier.getCarrier_Price());
            existingCarrier.setCarrier_manager(carrier.getCarrier_manager());
            carrierService.registerCarrier(existingCarrier);
        } else {

            carrierService.registerCarrier(carrier);
        }

        if(checkCarrier(carrier)) {
            shipment.setState(shipment_state.completed);
            shipment.setCreatedAt(LocalDateTime.now());
            shipmentService.save(shipment);
        }



        redirectAttributes.addFlashAttribute("message", "성공적으로 등록되었습니다.");
        redirectAttributes.addAttribute("shipment_Number", shipment_Number);

        return "redirect:/shipment/confirmedList";
    }



    public boolean checkCarrier(carrier carrier) {
        //carrier의 모든 데이터 베이스 칼럼이 입력되었으면
        return carrier.getCarrier_Name() != null && !carrier.getCarrier_Name().isEmpty()
                && carrier.getCarrier_PhoneNumber() != null && !carrier.getCarrier_PhoneNumber().isEmpty()
                && carrier.getCarrier_Vehicle() != null && !carrier.getCarrier_Vehicle().isEmpty()
                && carrier.getCarrier_Price() != null
                && carrier.getCarrier_manager() != null && !carrier.getCarrier_manager().isEmpty();
    }



}