package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.obtainorder_state;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private obtainorder_number orderNumber;

    private String writer;

    private String productName;

    private LocalDate orderDate;

    private Long order_Amount;

    @Enumerated(EnumType.STRING)
    private obtainorder_state state;

    private LocalDate delivery_Date;

    private String company_name;


}
