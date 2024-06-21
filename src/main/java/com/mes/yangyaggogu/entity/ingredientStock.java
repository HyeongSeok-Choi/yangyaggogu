package com.mes.yangyaggogu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.constant.rowStock_state;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ingredientStock {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materials_Name;

    private String ingredient_Code;

    private Long ingredient_Amount;

    @Enumerated(EnumType.STRING)
    private rowStock_state state;

//    private String reason;

//    private Long avaliable_Amount;

//    private Long out_Amount;

    @JoinColumn(name = "orderNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    private obtainorder_number order_Number;

    private String company_code;

    private String company_name;

    private LocalDate in_date;


}
