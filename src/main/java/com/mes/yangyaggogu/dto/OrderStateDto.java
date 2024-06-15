package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.entity.obtainorder_number;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderStateDto {

    private Long id;

    private obtainorder_number orderNumber;

    private String company_name;

    private String productName;

    private LocalDateTime order_Date;

    private Long order_Amount;

    private LocalDateTime delivery_Date;

    private obtainorder_state state;

    private String writer;

}
