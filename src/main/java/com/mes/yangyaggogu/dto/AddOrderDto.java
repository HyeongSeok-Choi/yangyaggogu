package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.entity.obtainorder_detail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderDto {

    private String company_name;

    private String productName;

    private LocalDate order_Date;

    private Long order_Amount;

    private LocalDate delivery_Date;

    private String writer;

    //생성자를 사용해 객체 생성하기
    public obtainorder_detail toEntity() {
        return obtainorder_detail.builder()
                .company_name(company_name)
                .productName(productName)
                .orderDate(order_Date)
                .order_Amount(order_Amount)
                .delivery_Date(delivery_Date)
                .writer(writer)
                .build();
    }
}
