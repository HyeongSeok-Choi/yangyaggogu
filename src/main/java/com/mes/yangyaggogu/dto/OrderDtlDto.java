package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtlDto {

    private Long id;

    private String company_name;

    private obtainorder_number orderNumber;

    private String productName;

    private Long order_Amount;

    private LocalDate delivery_Date;

    //entity -> dto변환
    public OrderDtlDto(obtainorder_detail obtainorderDetail) {
        this.id = obtainorderDetail.getId();
        this.company_name = obtainorderDetail.getCompany_name();
        this.orderNumber = obtainorderDetail.getOrderNumber();
        this.productName = obtainorderDetail.getProductName();
        this.order_Amount = obtainorderDetail.getOrder_Amount();
        this.delivery_Date = obtainorderDetail.getDelivery_Date();
    }
}
