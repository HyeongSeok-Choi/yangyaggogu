package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.searchForm;
import com.mes.yangyaggogu.service.productPlanService;
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
    private final productPlanService productPlanservice;

    @GetMapping(value = "/rowMaterial")
    public String rowMaterial(Model model, searchDto searchDto){

        rowStockService.getRowStockList();

        model.addAttribute("searchDto", searchDto);


        return "/stockPlan/rowMaterial_Table";
    }


    @GetMapping(value = "/rowMaterialOrder")
    public String rowOrder(){

        rowStockService.getRowStockList();

        return "/stockPlan/rowMaterial_Order";
    }

    @GetMapping(value = "/rowStockOrderRegister")
    public String orderRegister(Model model, String ProductPlanCode ) throws Exception{


       List<productPlan> productPlanList = productPlanservice.getProductPlansBeforeOrder();

       model.addAttribute("productPlanListBefore", productPlanList);

       if(ProductPlanCode != null){

           if(ProductPlanCode.equals("선택없음")){

               model.addAttribute("ProductPlan", new productPlan());

           }else {
               productPlan findProductPlan = productPlanservice.getProductPlan(ProductPlanCode);
               model.addAttribute("ProductPlanCode", ProductPlanCode);
               model.addAttribute("ProductPlan", findProductPlan);
           }
       }else {
           model.addAttribute("ProductPlan", new productPlan());
       }


        return "/stockPlan/rowStockOrderRegister";
    }

    @GetMapping(value = "/BomList")
    public String bomList(){
        return "/stockPlan/bomList_Table";
    }

}
