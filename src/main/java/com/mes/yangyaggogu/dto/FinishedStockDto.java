package com.mes.yangyaggogu.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class FinishedStockDto {

    private String orderNumber;
    private String productName;
    private Long productAmount;
    private String exp;
}
