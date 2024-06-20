package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderStateDto {

    private Long id;

    private String orderNumber;

    private String company_name;

    private String productName;

    private LocalDate order_Date;

    private Long order_Amount;

    private LocalDate delivery_Date;

    private obtainorder_state state;

    private String writer;

    public OrderStateDto(obtainorder_detail obtainorder_detail) {
        this.id = obtainorder_detail.getId();
        this.orderNumber = obtainorder_detail.getOrderNumber().getOrder_Number();
        this.company_name = obtainorder_detail.getCompany_name();
        this.productName = obtainorder_detail.getProductName();
        this.order_Date = obtainorder_detail.getOrderDate();
        this.order_Amount = obtainorder_detail.getOrder_Amount();
        this.delivery_Date = obtainorder_detail.getDelivery_Date();
        this.state = obtainorder_detail.getState();
        this.writer = obtainorder_detail.getWriter();

    }

}
