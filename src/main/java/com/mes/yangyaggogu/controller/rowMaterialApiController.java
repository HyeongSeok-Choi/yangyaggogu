package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.service.productPlanService;
import com.mes.yangyaggogu.service.rowStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class rowMaterialApiController {

    private final rowStockService rowStockService;

    //생산계획표 모두 출력
    @GetMapping(value = "/getRowStockList")
    public Map<String, Object> rowStockList() {

        //datatable 사용시 data를 키로 가져야 해서 넣음
        Map<String, Object> rowStock = new HashMap<String, Object>();

        rowStock.put("data", rowStockService.getRowStockList());

        return rowStock;
    }

    @PostMapping(value = "/registerRowStack")
    public ResponseEntity<Map<String, Object>> rowStockList(@RequestBody StockDto stockDto) {

        System.out.println("들어옸어?");

        System.out.println(stockDto.getIngredientCode());
        System.out.println(stockDto.getCompanyName());
        System.out.println(stockDto.getMaterialsName());
        System.out.println(stockDto.getIngredientAmount());

        HashMap<String, Object> rowStock = new HashMap<>();
        rowStock.put("StockDto", stockDto);

        rowStockService.saveStock(stockDto);

        return ResponseEntity.ok(rowStock);
    }

    @PostMapping(value = "/rowMaterial/search")
    public ResponseEntity<?> SearchRowStockList(@RequestBody searchDto search) {

        System.out.println("아예 안오니 ?");

        System.out.println(search.getEnd());
        System.out.println(search.getStart());
        System.out.println(search.getKeyword());

        List<ingredientStock> searchLists =rowStockService.searchLists(search);

//        HashMap<String, Object> SearchRowStock = new HashMap<>();
//        SearchRowStock.put("data",searchLists);

        return ResponseEntity.ok(searchLists);
    }


}
