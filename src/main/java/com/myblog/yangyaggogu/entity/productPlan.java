package com.myblog.yangyaggogu.entity;

import com.myblog.yangyaggogu.constant.productionPlan_state;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private LocalDateTime P_startDate;

    private LocalDateTime P_endDate;

    private Long target_Output;

    private Long now_Output;

    @Enumerated(EnumType.STRING)
    private productionPlan_state state;
}
