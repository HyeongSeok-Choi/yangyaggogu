package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.searchForm;
import com.mes.yangyaggogu.service.rowStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class rowMaterialController {

    private final rowStockService rowStockService;
    @GetMapping(value = "/rowMaterial")
    public String rowMaterial(Model model, searchDto searchDto){

        rowStockService.getRowStockList();

        model.addAttribute("searchDto", searchDto);


        return "/stockPlan/rowMaterial_Table";
    }


    @GetMapping(value = "/rowStockRegister")
    public String register() throws Exception{

        System.out.println("성공?..........");

        return "/stockPlan/rowStackRegister";
    }

}
