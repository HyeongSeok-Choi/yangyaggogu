package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.constant.rowStock_state;
import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.*;
import com.mes.yangyaggogu.repository.ingredientStockRepository;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import com.mes.yangyaggogu.repository.wrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class rowStockService {


    private final ingredientStockRepository ingredientStockRepository;
    private final productPlanService productPlanService;
    private final wrapRepository wrapRepository;
    private final obtainorder_detailRepository obtainorder_detailRepository;
    private final obtainorder_numberRepository obtainorder_NumberRepository;


    public List<ingredientStock> getRowStockList(){

        return ingredientStockRepository.findAll();
    }
    public List<wrap> getWrapkList(){

        return wrapRepository.findAll();
    }

//    public ingredientStock saveStock(StockDto stockDto){
//        ingredientStock ingredientStock = new ingredientStock();
////        obtainorder_number obtainorder_number = new obtainorder_number();
////        ingredientStock.setOrder_Number(obtainorder_number);
//
//        ingredientStock.setIngredient_Code(stockDto.getIngredientCode());
//        ingredientStock.setMaterials_Name(stockDto.getMaterialsName());
//        ingredientStock.setIngredient_Amount(stockDto.getIngredientAmount());
//        ingredientStock.setCompany_name(stockDto.getCompanyName());
//
//        LocalDate date = LocalDate.parse(stockDto.getInDate());
//        ingredientStock.setIn_date(date);
//
//        return ingredientStockRepository.save(ingredientStock);
//
//    }

    public ingredientStock orderStock(StockDto stockDto){


        ingredientStock ingredientStock = new ingredientStock();
        System.out.println(stockDto.getProductPlanCodes()+"요거");
        productPlan productPlan =productPlanService.getProductPlan(stockDto.getProductPlanCodes());
        productPlan.setState(productionPlan_state.ready);
        productPlanService.save(productPlan);

        ingredientStock.setProductionPlanCode(productPlan);

//        if(stockDto.getMaterialsName().equals("흑마늘즙") || stockDto.getMaterialsName().equals("양배추즙")){
//            String name = stockDto.getMaterialsName();
//            ingredientStock.setMaterials_Name(name.substring(0,3));
//        }else{
//            String name = stockDto.getMaterialsName();
//            ingredientStock.setMaterials_Name(name.substring(0,2) + "농축액");
//        }
        ingredientStock.setMaterials_Name(stockDto.getMaterialsName());
        ingredientStock.setIngredient_Amount(stockDto.getIngredientAmount());
        ingredientStock.setCompany_name(stockDto.getCompanyName());
        ingredientStock.setOrder_date(stockDto.getOrderDate());
        ingredientStock.setSubMaterialsAmount(stockDto.getSubMaterialsAmount());
        ingredientStock.setSubMaterialsName(stockDto.getSubMaterialsName());


        return ingredientStockRepository.save(ingredientStock);

    }

    public wrap boxWrapOrder(wrap wrap){


        return wrapRepository.save(wrap);
    }

    public LocalDate deliveryDate(String productCode){

      String[] orderNum =  productCode.split(",");

        System.out.println(orderNum[0].substring(0,orderNum[0].length() - 2)+"매그네릭");
        obtainorder_number findObtainNum =obtainorder_NumberRepository.findById(orderNum[0].substring(0,orderNum[0].length() - 2)).orElseThrow();

       List<obtainorder_detail> obtainorderDetailList = obtainorder_detailRepository.findByOrderNumber(findObtainNum);


       LocalDate deliveryDate = null;
       for (obtainorder_detail obtainorderDetail:obtainorderDetailList){
           deliveryDate= obtainorderDetail.getDelivery_Date();
       }
       
       return deliveryDate;

    }

    public boolean checkPossibleIngOrder(StockDto stockDto){
        List<ingredientStock> ingreList =ingredientStockRepository.getByOrder_dateAndIngredient_Amount(stockDto.getOrderDate(),stockDto.getMaterialsName(), stockDto.getCompanyName());

        Long TotalAmount=0L;

        for (ingredientStock sd:ingreList){
            TotalAmount+=sd.getIngredient_Amount();
            System.out.println(TotalAmount+"수퍼이끌림");
        }

        TotalAmount+=stockDto.getIngredientAmount();

        if(stockDto.getMaterialsName().equals("양배추즙") || stockDto.getMaterialsName().equals("흑마늘즙")) {
            if (TotalAmount > 2000L ) {
                return false;
            } else {
                return true;
            }
        }else{
            if (TotalAmount > 2000000L ) {
                System.out.println("여기 오냐고");
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean checkPossibleIngOrderBox(wrap wrap){
        List<wrap> ingreList =wrapRepository.getCompanyList(wrap.getOrder_date(),wrap.getSubMaterialsName(), wrap.getCompanyName());

        Long TotalAmount=0L;

        for (wrap sd:ingreList){
            TotalAmount+=sd.getSubMaterialsAmount();
            System.out.println(TotalAmount+"수퍼이끌림");
        }

        TotalAmount+=wrap.getSubMaterialsAmount();

        if(wrap.getSubMaterialsName().equals("포장지")){

            if(TotalAmount>100000L || TotalAmount<10000L){
                return false;
            }


        }else{
            if(TotalAmount>10000L || TotalAmount<1000L){
                return false;
            }
        }

        return true;
    }


    public List<StockDto> searchLists(searchDto searchDto){

        List<StockDto> searchLists= ingredientStockRepository.getRowStockPage(searchDto.getStart(),searchDto.getEnd(),searchDto.getKeyword())
                .stream()
                .map(a -> new StockDto(a))
                .collect(Collectors.toList());


        return searchLists;
    }

    public ingredientStock updateRowStockUpdate(Long id, String state){

       ingredientStock rowStock = ingredientStockRepository.findAllById(id)
               .orElseThrow(() -> new RuntimeException("RowStock not found"));
        rowStock.setState(rowStock_state.valueOf(state));
        if(rowStock.getMaterials_Name().equals("양배추즙")||rowStock.getMaterials_Name().equals("흑마늘즙")){
            LocalDate inDate = rowStock.getOrder_date().plusDays(2);
            rowStock.setIn_date(inDate);
            System.out.println("inDate"+inDate);


            if(inDate.getDayOfWeek().getValue() == 6){
                rowStock.setIn_date(inDate.plusDays(2));
                System.out.println("inDate"+inDate);
            }else if(inDate.getDayOfWeek().getValue() == 7){
                rowStock.setIn_date(inDate.plusDays(1));
                System.out.println("inDate"+inDate);
            }

        }else{
            LocalDate inDate = rowStock.getOrder_date().plusDays(3);
            rowStock.setIn_date(inDate);
            System.out.println("inDate"+inDate);

            if(inDate.getDayOfWeek().getValue() == 6){
                rowStock.setIn_date(inDate.plusDays(2));
                System.out.println("inDate"+inDate);
            }else if(inDate.getDayOfWeek().getValue() == 7){
                rowStock.setIn_date(inDate.plusDays(1));
                System.out.println("inDate"+inDate);
            }
        }
        return ingredientStockRepository.save(rowStock);
    }
}
