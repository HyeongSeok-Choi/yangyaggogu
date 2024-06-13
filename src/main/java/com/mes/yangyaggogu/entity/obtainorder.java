package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.obtainorder_state;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class obtainorder {

    @Id
    private String order_Number;

    private String writer;

    private String productName;

    private LocalDateTime order_Date;

    private Long order_Amount;

    private obtainorder_state state;

    private LocalDateTime delivery_Date;

    private String company_code;

    private String company_name;


}
