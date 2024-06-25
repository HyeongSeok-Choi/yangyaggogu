package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.constant.rowStock_state;
import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.wrap;
import com.mes.yangyaggogu.repository.ingredientStockRepository;
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

    public boolean checkPossibleIngOrder(StockDto stockDto){
        List<ingredientStock> ingreList =ingredientStockRepository.getByOrder_dateAndIngredient_Amount(stockDto.getOrderDate(),stockDto.getMaterialsName(), stockDto.getCompanyName());

        Long TotalAmount=0L;

        for (ingredientStock sd:ingreList){
            TotalAmount+=sd.getIngredient_Amount();
            System.out.println(TotalAmount+"수퍼이끌림");
        }

        TotalAmount+=stockDto.getIngredientAmount();

        if(TotalAmount>2000L){
            return false;
        }else {
            return true;
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
        rowStock.setIn_date(LocalDate.now());


        return ingredientStockRepository.save(rowStock);
    }
}
