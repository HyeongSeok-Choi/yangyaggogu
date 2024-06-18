package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.shipment_state;
import com.mes.yangyaggogu.entity.finishedstock;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.service.finishedstockService;
import com.mes.yangyaggogu.service.shipmentService;
import com.mes.yangyaggogu.service.workOrderPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class shipmentApiController {

    final private finishedstockService finishedstockService;
    final private shipmentService shipmentService;

    @GetMapping("/shipment/list")
    public Map<String,Object> showShipmentRegisterForm() {
        Map<String,Object> map = new HashMap<>();
        map.put("data",finishedstockService.showFinishedStockList());

        return map;
    }


    @PostMapping("shipment/register")
    public ResponseEntity<?> registerShipment(@RequestBody List<Long>ids) {
        List<finishedstock> updatedStocks = new ArrayList<>();
        for (Long id : ids) { //list 돌면서 검사
            Optional<finishedstock> optionalFinishedStock = finishedstockService.findById(id);
            if (optionalFinishedStock.isPresent()) {
                finishedstock existingStock = optionalFinishedStock.get(); //존재하면 객체를 가져옴
                existingStock.setShipmentState("출하 완료");
                finishedstockService.save(existingStock);
                updatedStocks.add(existingStock);  //업데이트 된 객체를 리스트에 추가

                shipment shipment = new shipment();
                shipment.setShipment_Number(shipmentService.generateShipmentNumber());
                //나중에 형식 바꿀수도
                shipment.setOrder_Number(existingStock.getOrderNumber());
                shipment.setCompany_name("");
                shipment.setCompany_Address("");
                shipment.setCompany_code("");
                //
                shipment.setShipment_Amount(existingStock.getAmount());
                shipment.setProductionName(existingStock.getMaterials_Name());
                shipment.setShippingDate(LocalDateTime.now());
                //출하일
                shipment.setDeliveryDate(LocalDateTime.now());
                //납품일은 나중에 수주상태 테이블에서 받아오기
                shipment.setCreatedAt(null);
                shipment.setState(shipment_state.ready);

                shipmentService.save(shipment);



            }
        }
        if (updatedStocks.isEmpty()) {
            return ResponseEntity.notFound().build(); //업데이트 된 객체가 없으면 404 반환
        } else {
            return ResponseEntity.ok(updatedStocks);  // 업데이트 된 객체 리스트 반환
        }
  }


    @GetMapping("/shipment/confirmedList")
    public Map<String,Object> showShipmentList() {
        Map<String,Object> map = new HashMap<>();
        map.put("data",shipmentService.showShipmentList());

        return map;
    }




}
