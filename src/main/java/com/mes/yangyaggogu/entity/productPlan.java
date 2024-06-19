package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.productionPlan_state;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class productPlan {

    @Id
    private String productionPlanCode;

    private String materials_Name;

    @ManyToOne
    @JoinColumn(name = "order_Number")
    private obtainorder_number order_Number;

    private LocalDate P_startDate;

    private LocalDate P_endDate;

    private Long target_Output;

    private Long now_Output;

    @Enumerated(EnumType.STRING)
    private productionPlan_state state;




}
