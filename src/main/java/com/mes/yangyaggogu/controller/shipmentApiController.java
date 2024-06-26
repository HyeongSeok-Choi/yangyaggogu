package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.constant.shipment_state;
import com.mes.yangyaggogu.dto.FinishedStockDTO;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.dto.shipmentDTO;
import com.mes.yangyaggogu.dto.workOrderPlanDTO;
import com.mes.yangyaggogu.entity.*;
import com.mes.yangyaggogu.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public Map<String, Object> showShipmentRegisterForm() {
        Map<String, Object> map = new HashMap<>();

        List<FinishedStockDTO> finishedStockDTOList = finishedstockService.showFinishedStockList().stream()
                .map(a -> new FinishedStockDTO(a))
                .collect(Collectors.toList());
        map.put("data", finishedStockDTOList);

        return map;
    }


    @PostMapping("shipment/register")
    public ResponseEntity<?> registerShipment(@RequestBody List<Long> ids) {
        List<finishedstock> updatedStocks = new ArrayList<>();
        List<shipment> shipments = new ArrayList<>(); // 출하 객체 리스트 생성

        for (Long id : ids) { // 리스트 돌면서 검사

            System.out.println(id);
            Optional<finishedstock> optionalFinishedStock = finishedstockService.findById(id);

            if (optionalFinishedStock.isPresent()) {

                finishedstock existingStock = optionalFinishedStock.get(); // 존재하면 객체를 가져옴
                existingStock.setShipmentState("출하 완료");
                finishedstockService.save(existingStock);
                updatedStocks.add(existingStock); // 업데이트 된 객체를 리스트에 추가

                System.out.println(existingStock.getAmount());
                System.out.println(existingStock.getShipmentState());
                System.out.println(existingStock.getOrderNumber());

                obtainorder_number orderNumber = existingStock.getOrderNumber();
                List<obtainorder_detail> odList = obtainOrderService.findByOrderNumber(orderNumber);


                for (obtainorder_detail od : odList) { // 각 납품일에 대해 반복

                    shipment shipment = new shipment();
                    String newShipmentNumber;

                    // 새로운 shipment_Number가 고유한지 확인하는 루프
                    do {
                        newShipmentNumber = shipmentService.generateShipmentNumber();
                    } while (shipmentService.existsByShipmentNumber(newShipmentNumber));

                    shipment.setShipment_Number(newShipmentNumber);
                    shipment.setCompany_name(od.getCompany_name());
                    shipment.setCompany_Address(""); //

                    shipment.setOrder_Number(orderNumber);
                    shipment.setShipment_Amount(existingStock.getAmount());
                    shipment.setProductionName(existingStock.getMaterials_Name());
                    shipment.setShippingDate(LocalDateTime.now());
                    shipment.setDeliveryDate(od.getDelivery_Date());
                    shipment.setCreatedAt(null);
                    shipment.setState(shipment_state.ready);



                    shipmentService.save(shipment);

                    od.setState(obtainorder_state.completed);
                    obtainOrderService.save(od); // state completed로 변경

                    shipments.add(shipment); // 각 출하 객체를 리스트에 추가
                }
            }
        }

        if (updatedStocks.isEmpty()) {
            return ResponseEntity.notFound().build(); // 업데이트 된 객체가 없으면 404 반환
        } else {
            return ResponseEntity.ok(updatedStocks); // 업데이트 된 객체 리스트 반환
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


    @PostMapping(value = "/confirmedShipment/search")
    public ResponseEntity<?> SearchFinishedStockList(@RequestBody searchDto search) {

        System.out.println("아예 안오니 ?");

        System.out.println(search.getEnd());
        System.out.println(search.getStart());
        System.out.println(search.getKeyword());

        List<shipmentDTO> searchLists = shipmentService.searchLists(search);

        return ResponseEntity.ok(searchLists);
    }

}
