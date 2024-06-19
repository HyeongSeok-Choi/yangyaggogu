package com.mes.yangyaggogu.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class StockDto {

    private String orderNumber;
    private String ingredientCode;
    private String materialsName;
    private Long ingredientAmount;
    private String inDate;
    private String companyName;

}
