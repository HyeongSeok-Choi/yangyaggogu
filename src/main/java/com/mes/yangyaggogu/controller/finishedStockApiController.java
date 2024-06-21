package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.finishedstock_state;
import com.mes.yangyaggogu.dto.FinishedStockDTO;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.dto.shipmentDTO;
import com.mes.yangyaggogu.entity.finishedstock;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.service.finishedstockService;
import com.mes.yangyaggogu.service.workOrderPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class finishedStockApiController {


    final private workOrderPlanService workOrderPlanService;
    final private finishedstockService finishedstockService;

    @GetMapping("/order_list")
    public Map<String,Object> showOrderList() {
        Map<String,Object> map = new HashMap<>();
        map.put("data",workOrderPlanService.getAll());

        return map;
    }

    @GetMapping("/finishedStock/list")
    public Map<String,Object> showFinishedStockList() {
        Map<String,Object> map = new HashMap<>();

        List<FinishedStockDTO> finishedStockDTOList = finishedstockService.showFinishedStockList().stream()
                .map(a -> new FinishedStockDTO(a))
                .collect(Collectors.toList());
        map.put("data",finishedStockDTOList);

        return map;
    }

//    @PostMapping("finishedStock/update")
//    public ResponseEntity<?> updateFinishedStock(@RequestBody finishedstock finishedstock) {
//        Optional<finishedstock> optionalFinishedStock = finishedstockService.findById(finishedstock.getId());
//        if (optionalFinishedStock.isPresent()) {
//            finishedstock existingStock = optionalFinishedStock.get();
//            existingStock.setState(finishedstock.getState());
//            finishedstockService.save(existingStock);
//            return ResponseEntity.ok(existingStock);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping("finishedStock/update")
    public ResponseEntity<?> updateFinishedStock(@RequestBody FinishedStockDTO finishedStockDTO) {
        Optional<finishedstock> optionalFinishedStock = finishedstockService.findById(finishedStockDTO.getId());
        if (optionalFinishedStock.isPresent()) {
            finishedstock existingStock = optionalFinishedStock.get();
            existingStock.setState(finishedStockDTO.getState());
            finishedstockService.save(existingStock);
            FinishedStockDTO responseDTO = convertToDTO(existingStock);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private FinishedStockDTO convertToDTO(finishedstock stock) {
        FinishedStockDTO dto = new FinishedStockDTO();
        dto.setId(stock.getId());
        dto.setOrderNumber(stock.getOrderNumber().getOrderNumber());
        dto.setMaterials_Name(stock.getMaterials_Name());
        dto.setAmount(stock.getAmount());
        dto.setExp(stock.getExp());
        dto.setState(finishedstock_state.out);
        dto.setShipmentState(stock.getShipmentState());
        return dto;
    }


    @PostMapping(value = "/productMaterial/search")
    public ResponseEntity<?> SearchFinishedStockList(@RequestBody searchDto search) {

        System.out.println("아예 안오니 ?");

        System.out.println(search.getEnd());
        System.out.println(search.getStart());
        System.out.println(search.getKeyword());

        List<FinishedStockDTO> searchLists =finishedstockService.searchLists(search);

        return ResponseEntity.ok(searchLists);
    }
}
