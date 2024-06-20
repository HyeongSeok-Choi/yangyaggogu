package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.dto.OrderDtlDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.service.ObtainOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Ids;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class obtainorderApiController {

    private final ObtainOrderService obtainOrderService;

    //수주현황 조회
    @GetMapping(value = "/getObtainOrderList")
    public Map<String, Object> obtainOrderList() {
        Map<String, Object> O_order = new HashMap<>();

        O_order.put("data", obtainOrderService.getObtainOrderDtl());

        return O_order;
    }

    //등록
    @PostMapping("/addOrder")
    public ResponseEntity<?> AddOrder(@RequestBody List<AddOrderDto> addOrderDtoList){


            obtainOrderService.saveList(addOrderDtoList);


        return ResponseEntity.ok(addOrderDtoList);
    }

    //수주 확정 버튼 클릭 시 진행 상태 바꾸기
    @PostMapping("/changeObtainState")
    public ResponseEntity<?> changeObtainState(@RequestBody String[] targetIds){

        List<obtainorder_detail> obtainorder_details = new ArrayList<>();

        for (String id : targetIds){
            System.out.println(id);
        }

        for (String id : targetIds) {

            Long findId = Long.valueOf(id);

            obtainorder_detail findObtain = obtainOrderService.getObtainOrderDtlById(findId);

            findObtain.setState(obtainorder_state.confirmed);

            obtainOrderService.save(findObtain);

            obtainorder_details.add(findObtain);
        }
        return ResponseEntity.ok(obtainorder_details);
    }

    //팝업창 데이터 수정
    @PostMapping("/updateOrder")
    public ResponseEntity<Map<String, Object>> updateOrder(@RequestBody OrderDtlDto orderDtlDto){
        Map<String, Object> response = new HashMap<>();
        try {
            //주문 업데이트 로직 구현
            boolean success = obtainOrderService.updateOrder(orderDtlDto);
            response.put("success", success);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    //팝업창 데이터 삭제
    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = obtainOrderService.deleteOrder(id);
            response.put("success", success);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
