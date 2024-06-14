package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.productionPlan_state;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class productPlan {

    @Id
    private String productionPlanCode;

    @ManyToOne
    @JoinColumn(name = "order_Number")
    private obtainorder_number order_Number;

    private LocalDateTime P_startDate;

    private LocalDateTime P_endDate;

    private Long target_Output;

    private Long now_Output;

    private productionPlan_state state;


}
