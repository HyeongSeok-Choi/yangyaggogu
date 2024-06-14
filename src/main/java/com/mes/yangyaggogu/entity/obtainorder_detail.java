package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.obtainorder_state;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class obtainorder_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "order_Number")
    @ManyToOne
    private obtainorder_number orderNumber;

    private String writer;

    private String productName;

    private LocalDateTime order_Date;

    private Long order_Amount;

    private obtainorder_state state;

    private LocalDateTime delivery_Date;

    private String company_name;


}
