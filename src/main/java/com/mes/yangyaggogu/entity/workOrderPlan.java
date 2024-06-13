package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.workOrderPlan_state;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class workOrderPlan {

    @Id
    private String production_Plan_Id;

    @ManyToOne
    @JoinColumn(name = "productionPlanCode")
    private productPlan productPlan;

    @ManyToOne
    @JoinColumn(name = "order_Number")
    private obtainorder obtainorder;

    private String producer;

    private String processName;

    private LocalDateTime P_startDate;

    private LocalDateTime P_endDate;

    private Long target_Output;

    private Long now_Output;

    private workOrderPlan_state state;

    private String materials_Name;
}
