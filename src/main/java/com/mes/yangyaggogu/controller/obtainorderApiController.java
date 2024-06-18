package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.service.ObtainOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class obtainorderApiController {

    private final ObtainOrderService obtainOrderService;

    //조회
    @GetMapping(value = "/getObtainOrderList")
    public Map<String, Object> obtainOrderList() {
        Map<String, Object> O_order = new HashMap<>();

        O_order.put("data", obtainOrderService.getObtainOrderDtl());

        return O_order;
    }

    //등록
    @PostMapping("/addOrder")
    public ResponseEntity<?> AddOrder(@RequestBody AddOrderDto addOrderDto){

        System.out.println(addOrderDto.getOrder_Amount());
        System.out.println(addOrderDto.getCompany_name());
        System.out.println(addOrderDto.getDelivery_Date());
        System.out.println(addOrderDto.getWriter());
        System.out.println(addOrderDto.getProductName());
        System.out.println("왔니?");

//        for (AddOrderDto addOrderDto1: addOrderDto){
//            obtainOrderService.save(addOrderDto1);
//        }

        return ResponseEntity.ok("created Successfull");
    }
}
