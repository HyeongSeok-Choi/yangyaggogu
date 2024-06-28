package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.constant.company_state;
import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.company;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.searchForm;
import com.mes.yangyaggogu.service.CompanyService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class rowMaterialController {

    private final rowStockService rowStockService;
    private final productPlanService productPlanservice;
    private final CompanyService companyService;

    @GetMapping(value = "/rowMaterial")
    public String rowMaterial(Model model, searchDto searchDto){

        rowStockService.getRowStockList();

        model.addAttribute("searchDto", searchDto);


        return "stockPlan/rowMaterial_Table";
    }


    @GetMapping(value = "/rowMaterialOrder")
    public String rowOrder(){

        rowStockService.getRowStockList();

        return "stockPlan/rowMaterial_Order";
    }

    @GetMapping(value = "/rowStockOrderRegister")
    public String orderRegister(Model model, String ProductPlanCode, String companyName, String tradeGoods) throws Exception{


       List<productPlan> productPlanList = productPlanservice.getProductPlansBeforeOrder();
       List<String> companyList = new ArrayList<>();

       model.addAttribute("productPlanListBefore", productPlanList);

       if(ProductPlanCode != null){

           if(ProductPlanCode.equals("선택없음")){

               model.addAttribute("ProductPlan", new productPlan());

           }else {
               productPlan findProductPlan = productPlanservice.getProductPlan(ProductPlanCode);

               if(findProductPlan.getMaterialsName().equals("양배추즙")||findProductPlan.getMaterialsName().equals("흑마늘즙")){
                   model.addAttribute("subMaterials", "벌꿀");
                   model.addAttribute("subMaterialsAmount", findProductPlan.getTarget_Output()*150);
                   findProductPlan.setTarget_Output((1000 * findProductPlan.getTarget_Output())/250);
                   findProductPlan.setMaterialsName(findProductPlan.getMaterialsName().substring(0,3));

                   companyList = companyService.findByTradeGoods(company_state.delivery, "즙");


               }else{
                   model.addAttribute("subMaterials", "콜라겐");
                   model.addAttribute("subMaterialsAmount", findProductPlan.getTarget_Output()*50);
                   findProductPlan.setTarget_Output((findProductPlan.getTarget_Output()*25)*5);
                   findProductPlan.setMaterialsName(findProductPlan.getMaterialsName().substring(0,2)+"농축액");

                   companyList = companyService.findByTradeGoods(company_state.delivery, "젤리스틱");
               }

               if(companyName != null) {
                   if (companyName.equals("발주처를 선택해주세요")){
                       model.addAttribute("company", new company());
                   }else{
                       model.addAttribute("companyName", companyName);

                   }
               }else{
                   model.addAttribute("company", new company());
               }
               model.addAttribute("companyList",companyList);
               model.addAttribute("ProductPlanCode", ProductPlanCode);
               model.addAttribute("ProductPlan", findProductPlan);
           }
       }else {
           model.addAttribute("ProductPlan", new productPlan());
       }

        return "stockPlan/rowStockOrderRegister";
    }

    @GetMapping(value = "/boxWrap")
    public String boxWrap(Model model){

        List<String> companyList = companyService.findByTradeGoods(company_state.delivery, "박스/포장지");
        model.addAttribute("companyList",companyList);


        return "stockPlan/boxWrapRegister";
    }

    @GetMapping(value = "/BomList")
    public String bomList(){
        return "stockPlan/bomList_Table";
    }

    @GetMapping(value="/routingTable")
    public String routingTable(){
        return "ReferenceInfo/routing_Table";
    }

}
