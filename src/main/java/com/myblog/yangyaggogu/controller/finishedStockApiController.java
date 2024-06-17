package com.myblog.yangyaggogu.controller;

import com.myblog.yangyaggogu.entity.finishedstock;
import com.myblog.yangyaggogu.service.finishedstockService;
import com.myblog.yangyaggogu.service.workOrderPlanService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class finishedStockApiController {


    final private workOrderPlanService workOrderPlanService;
    final private finishedstockService finishedstockService;

    @GetMapping("/order_list")
    public Map<String,Object> showOrderList() {
        Map<String,Object> map = new HashMap<>();
        map.put("data",workOrderPlanService.showWorkOrderPlan());

        return map;
    }

    @GetMapping("finishedStock/list")
    public Map<String,Object> showFinishedStockList() {
        Map<String,Object> map = new HashMap<>();
        map.put("data",finishedstockService.showWorkOrderPlan());

        return map;
    }

    @PostMapping("finishedStock/update")
    public ResponseEntity<?> updateFinishedStock(@RequestBody finishedstock finishedstock) {
        Optional<finishedstock> optionalFinishedStock = finishedstockService.findById(finishedstock.getId());
        if (optionalFinishedStock.isPresent()) {
            finishedstock existingStock = optionalFinishedStock.get();
            existingStock.setState(finishedstock.getState());
            finishedstockService.save(existingStock);
            return ResponseEntity.ok(existingStock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
