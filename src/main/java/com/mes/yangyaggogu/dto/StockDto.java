package com.mes.yangyaggogu.dto;


import com.mes.yangyaggogu.constant.rowStock_state;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.obtainorder_number;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {

    private Long id;
    private String orderNumber;
    private String ingredientCode;
    private String materialsName;
    private Long ingredientAmount;
    private LocalDate inDate;
    private String companyName;
    private rowStock_state state;
    private String companyCode;

    public StockDto(ingredientStock ingredientStock){

        this.id = ingredientStock.getId();
        this.orderNumber = ingredientStock.getOrder_Number().getOrderNumber();
        this.ingredientCode = ingredientStock.getIngredient_Code();
        this.materialsName = ingredientStock.getMaterials_Name();
        this.ingredientAmount = ingredientStock.getIngredient_Amount();
        this.inDate = ingredientStock.getIn_date();
        this.companyName = ingredientStock.getCompany_name();
        this.state = ingredientStock.getState();
        this.companyCode = ingredientStock.getCompany_code();

    }

}
