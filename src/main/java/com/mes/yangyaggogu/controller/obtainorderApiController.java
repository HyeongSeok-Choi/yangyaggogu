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
    private final com.mes.yangyaggogu.service.workOrderPlanService workOrderPlanService;

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

        //반복문을 돌며 id에 해당되는 entity의 상태가 confirmed로 바뀜
        for (String id : targetIds) {

            Long findId = Long.valueOf(id);

            obtainorder_detail findObtain = obtainOrderService.getObtainOrderDtlById(findId);


           boolean check =  obtainOrderService.checkPossibleDay(findObtain.getDelivery_Date(),findObtain.getProductName());

           if(!check){
               return ResponseEntity.ok(check);
           }

            findObtain.setState(obtainorder_state.confirmed);

            obtainOrderService.save(findObtain);

            obtainorder_details.add(findObtain);
        }

        //수주 확정된 데이터의 생산계획이 자동적으로 만들어짐
        workOrderPlanService.MakeWorkOrderPlanData(obtainorder_details);


        return ResponseEntity.ok(obtainorder_details);
    }
}
