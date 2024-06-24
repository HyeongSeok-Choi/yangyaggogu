package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.dto.OrderDtlDto;
import com.mes.yangyaggogu.dto.OrderStateDto;
import com.mes.yangyaggogu.dto.workOrderPlanDTO;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.service.ObtainOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.expression.Ids;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

        List<OrderStateDto> OrderStateDtoList = obtainOrderService.getObtainOrderDtl().stream()
                .map(a -> new OrderStateDto(a))
                .collect(Collectors.toList());


        O_order.put("data", OrderStateDtoList);

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


        //id와 연관된 디테일 리스트 선언
        List<obtainorder_detail> obtainorder_details = new ArrayList<>();

        //반복문을 돌며 id에 해당되는 entity의 상태가 confirmed로 바뀜
        for (String id : targetIds) {

            Long findId = Long.valueOf(id);

            //id에 해당하는 디테일 객체 찾아옴
            obtainorder_detail findObtain = obtainOrderService.getObtainOrderDtlById(findId);

            //해당 디테일 객체의 시작일 계산
            //시작일이 주말이 포함되면 이틀이 더 소요
            LocalDate StartDay =obtainOrderService.returnStartday(findObtain);

            //합치는게 가능하다면 ?
            if(obtainOrderService.checkPossibleAddPlan(findObtain)){

                productPlan productPlan = obtainOrderService.JoinProductPlan(StartDay,findObtain);

                if(productPlan!=null){
                    findObtain.setState(obtainorder_state.confirmed);

                    obtainOrderService.save(findObtain);

                    workOrderPlanService.MakeWorkOrderPlanDataJoined(productPlan);

                  return   ResponseEntity.ok(productPlan);
                }
            };

            //3라인 체크
           boolean check =  obtainOrderService.checkPossibleDay(findObtain);

           //3라인 체크에 걸린다면
           if(!check){
               //안되는걸로 !
               return ResponseEntity.ok(check);
           }

           //3라인 체크에 안걸린다면 확정
            findObtain.setState(obtainorder_state.confirmed);

            obtainOrderService.save(findObtain);

            obtainorder_details.add(findObtain);

        }

        //수주 확정된 데이터의 생산계획이 자동적으로 만들어짐
        workOrderPlanService.MakeWorkOrderPlanData(obtainorder_details);


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

    //엑셀 업로드 후 DB 저장
    /*@PostMapping("excel_upload_order")
    public String registExcelOrder(@RequestParam("file") MultipartFile file){
        return obtainOrderService.upload(file);
    }*/
    @PostMapping("/uploadExcel")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
        List<AddOrderDto> addOrderDtoList = obtainOrderService.ExcelFileUpload(file);
        return ResponseEntity.ok(addOrderDtoList);
    }
}
