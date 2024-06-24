package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.service.rowStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class rowMaterialApiController {

    private final rowStockService rowStockService;

    @GetMapping(value = "/getRowStockList")
    public Map<String, Object> rowStockList() {

        //datatable 사용시 data를 키로 가져야 해서 넣음
        Map<String, Object> rowStock = new HashMap<String, Object>();

        List<StockDto> rowMaterialList = rowStockService.getRowStockList().stream()
                        .map(a -> new StockDto(a))
                                .collect(Collectors.toList());

        rowStock.put("data", rowMaterialList);


        return rowStock;
    }

    @GetMapping(value = "/getRowStockOrderList")
    public Map<String, Object> rowStockOrderList() {

        //datatable 사용시 data를 키로 가져야 해서 넣음
        Map<String, Object> rowOrderStock = new HashMap<String, Object>();

        List<StockDto> getRowStockOrderList = rowStockService.getRowStockList().stream()
                        .map(a->new StockDto(a))
                                .collect(Collectors.toList());
        rowOrderStock.put("data", getRowStockOrderList);

        return rowOrderStock;
    }


//    @PostMapping(value = "/registerRowStack")
//    public ResponseEntity<Map<String, Object>> rowStockList(@RequestBody StockDto stockDto) {
//
//        System.out.println("들어옸어?");
//
//        System.out.println(stockDto.getIngredientCode());
//        System.out.println(stockDto.getCompanyName());
//        System.out.println(stockDto.getMaterialsName());
//        System.out.println(stockDto.getIngredientAmount());
//
//        HashMap<String, Object> rowStock = new HashMap<>();
//        rowStock.put("StockDto", stockDto);
//
//        rowStockService.saveStock(stockDto);
//
//        return ResponseEntity.ok(rowStock);
//    }



    @PostMapping(value = "/registerOrderRowStack")
    public ResponseEntity<Map<String, Object>> rowStockOrderList(@RequestBody StockDto stockDto) {


        HashMap<String, Object> rowOrderStock = new HashMap<>();

        rowOrderStock.put("StockDto", stockDto);

        rowStockService.orderStock(stockDto);

        return ResponseEntity.ok(rowOrderStock);
    }

    @PostMapping(value = "/rowMaterial/search")
    public ResponseEntity<?> SearchRowStockList(@RequestBody searchDto search) {

        System.out.println("아예 안오니 ?");

        System.out.println(search.getEnd());
        System.out.println(search.getStart());
        System.out.println(search.getKeyword());

        List<StockDto> searchLists =rowStockService.searchLists(search);

//        HashMap<String, Object> SearchRowStock = new HashMap<>();
//        SearchRowStock.put("data",searchLists);

        return ResponseEntity.ok(searchLists);
    }

    @PostMapping("/updateRowStockState")
    public ResponseEntity<?> updateRowStockState(@RequestBody ingredientStock ingredientStock){

        System.out.println("들어왔어?");

        System.out.println(ingredientStock.getId());
        System.out.println(ingredientStock.getState());


        ingredientStock rowStockInputList = rowStockService.updateRowStockUpdate(ingredientStock.getId(), String.valueOf(ingredientStock.getState()));
        return ResponseEntity.ok().body(rowStockInputList);

    }

    @PostMapping("/updateRowStockOutState")
    public ResponseEntity<?> updateRowStockOutState(@RequestBody ingredientStock ingredientStock){

        System.out.println("들어왔어?");

        System.out.println(ingredientStock.getId());
        System.out.println(ingredientStock.getState());


        ingredientStock rowStockOutputList = rowStockService.updateRowStockUpdate(ingredientStock.getId(), String.valueOf(ingredientStock.getState()));
        return ResponseEntity.ok().body(rowStockOutputList);

    }


}
