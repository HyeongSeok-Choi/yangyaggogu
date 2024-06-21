package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.rowStock_state;
import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.repository.ingredientStockRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class rowStockService {


    private final ingredientStockRepository ingredientStockRepository;
    private final obtainorder_numberRepository obtainorderNumberRepository;

    public List<ingredientStock> getRowStockList(){

        return ingredientStockRepository.findAll();
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
        obtainorder_number obtainorder_number = new obtainorder_number();
        obtainorder_number.setOrder_Number(stockDto.getOrderNumber());
        obtainorderNumberRepository.save(obtainorder_number);

        ingredientStock.setOrder_Number(obtainorder_number);


        ingredientStock.setIngredient_Code(stockDto.getIngredientCode());
        ingredientStock.setMaterials_Name(stockDto.getMaterialsName());
        ingredientStock.setIngredient_Amount(stockDto.getIngredientAmount());
        ingredientStock.setCompany_name(stockDto.getCompanyName());


        return ingredientStockRepository.save(ingredientStock);

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
