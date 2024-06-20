package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.shipment_state;
import com.mes.yangyaggogu.dto.FinishedStockDTO;
import com.mes.yangyaggogu.dto.shipmentDTO;
import com.mes.yangyaggogu.dto.workOrderPlanDTO;
import com.mes.yangyaggogu.entity.*;
import com.mes.yangyaggogu.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class shipmentApiController {

    final private finishedstockService finishedstockService;
    final private shipmentService shipmentService;
    final private ObtainOrderService obtainOrderService;

    @GetMapping("/shipment/list")
    public Map<String,Object> showShipmentRegisterForm() {
        Map<String,Object> map = new HashMap<>();

        List<FinishedStockDTO> finishedStockDTOList = finishedstockService.showFinishedStockList().stream()
                .map(a -> new FinishedStockDTO(a))
                .collect(Collectors.toList());
        map.put("data",finishedStockDTOList);

        return map;
    }


    @PostMapping("shipment/register")
    public ResponseEntity<?> registerShipment(@RequestBody List<Long> ids) {
        List<finishedstock> updatedStocks = new ArrayList<>();
        for (Long id : ids) { //list 돌면서 검사
            Optional<finishedstock> optionalFinishedStock = finishedstockService.findById(id);
            if (optionalFinishedStock.isPresent()) {
                finishedstock existingStock = optionalFinishedStock.get(); //존재하면 객체를 가져옴
                existingStock.setShipmentState("출하 완료");
                finishedstockService.save(existingStock);
                updatedStocks.add(existingStock);  //업데이트 된 객체를 리스트에 추가

                shipment shipment = new shipment();
                obtainorder_number orderNumber = existingStock.getOrderNumber();
                System.out.println(orderNumber+"!!!!!!!!");
                List<obtainorder_detail> odList = obtainOrderService.findByOrderNumber(orderNumber);

                shipment.setShipment_Number(shipmentService.generateShipmentNumber());

                if (odList != null && !odList.isEmpty()) {
                    // 각 거래처별로 적절한 데이터를 처리
                    Map<String, obtainorder_detail> companyDetailsMap = new HashMap<>();
                    for (obtainorder_detail od : odList) {
                        companyDetailsMap.put(od.getCompany_name(), od);
                    }

                    // 여기서는 예를 들어 첫 번째 거래처 데이터를 선택합니다.
                    obtainorder_detail od = companyDetailsMap.values().iterator().next();

                    shipment.setCompany_name(od.getCompany_name());
                    shipment.setCompany_Address("");
                } else {
                    shipment.setCompany_name(null);
                    shipment.setCompany_Address("");
                }
                shipment.setOrder_Number(orderNumber);
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


        List<shipmentDTO> shipmentDtoList = shipmentService.showShipmentList().stream()
                .map(a -> new shipmentDTO(a))
                .collect(Collectors.toList());

        map.put("data",shipmentDtoList);

        return map;
    }

    @GetMapping("/shipment/confirmedList/{id}")
    public shipment getShipment(@PathVariable String id) {
        return shipmentService.findById(id);
    }

}
