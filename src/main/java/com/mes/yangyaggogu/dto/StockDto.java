package com.mes.yangyaggogu.dto;


import com.mes.yangyaggogu.constant.rowStock_state;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.wrap;
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
    private String materialsName;
    private String subMaterialsName;
    private Long subMaterialsAmount;
    private Long ingredientAmount;
    private LocalDate inDate;
    private String companyName;
    private rowStock_state state;
    private String productPlanCodes;
    private LocalDate orderDate;

    public StockDto(ingredientStock ingredientStock){

        this.id = ingredientStock.getId();
        this.productPlanCodes = ingredientStock.getProductionPlanCode().getProductionPlanCode();
        this.materialsName = ingredientStock.getMaterials_Name();
        this.subMaterialsName = ingredientStock.getSubMaterialsName();
        this.subMaterialsAmount = ingredientStock.getSubMaterialsAmount();
        this.ingredientAmount = ingredientStock.getIngredient_Amount();
        this.inDate = ingredientStock.getIn_date();
        this.companyName = ingredientStock.getCompany_name();
        this.state = ingredientStock.getState();
        this.orderDate = ingredientStock.getOrder_date();

    }

    public StockDto(wrap wrap){

      this.id = wrap.getId();
      this.companyName=wrap.getCompanyName();
      this.subMaterialsName=wrap.getSubMaterialsName();
      this.subMaterialsAmount=wrap.getSubMaterialsAmount();
      this.inDate=wrap.getIn_date();
      this.orderDate=wrap.getOrder_date();

    }

}
