package com.myblog.yangyaggogu.entity;

import com.myblog.yangyaggogu.constant.workOrderPlan_state;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String processCode;

    @ManyToOne
    @JoinColumn(name = "productionPlanCode")
    private productPlan productPlanCode;

    @ManyToOne
    @JoinColumn(name = "order_Number")
    private obtainorder_number obtainorder_number;

    private String producer;

    private String processName;

    private LocalDateTime P_startDate;

    private LocalDateTime P_endDate;

    private Long target_Output;

    private Long now_Output;

    @Enumerated(EnumType.STRING)
    private workOrderPlan_state state;

    private String materials_Name;
}
