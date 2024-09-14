package com.mes.yangyaggogu.controller;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mes.yangyaggogu.dto.productPlanDTO;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.dto.workOrderPlanDTO;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.service.productPlanService;
import com.mes.yangyaggogu.service.workOrderPlanService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class workOrderPlanAPIcontroller {

    private final workOrderPlanService workOrderPlanService;

    //작업지시서 모두 출력
    @GetMapping(value = "/getWorkOrderPlanList")
    public Map<String, Object> workOrderPlanList() {

        //datatable 사용시 data를 키로 가져야 해서 넣음
        Map<String, Object> w_plans = new HashMap<String, Object>();

        List<workOrderPlanDTO> workOrderPlanDTOList = workOrderPlanService.getAll().stream()
                .map(a -> new workOrderPlanDTO(a))
                .collect(Collectors.toList());


        w_plans.put("data",workOrderPlanDTOList);

        return w_plans;
    }

    //작업 시작
    @PostMapping(value = "/Start_workOrder/{id}")
    public ResponseEntity<?> Start_workOrder(@PathVariable Long id ,@RequestParam(name = "producer") String producer){

        String workOrNWork =workOrderPlanService.start_Work(id,producer);

        searchDto searchDto = new searchDto();
        searchDto.setKeyword(workOrNWork);


        return ResponseEntity.ok().body(searchDto);
    }

    //작업 종료
    @PostMapping(value = "/Stop_workOrder/{id}")
    public ResponseEntity<?> Stop_workOrder(@PathVariable Long id){

        workOrderPlanService.stop_Work(id);

        return ResponseEntity.ok().body("good");
    }



}
