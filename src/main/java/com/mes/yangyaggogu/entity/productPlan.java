package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.productionPlan_state;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class productPlan {

    @Id
    private String productionPlanCode;

    private String materialsName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_Number")
    private obtainorder_number orderNumber;

    private LocalDate pstartDate;

    private LocalDate pendDate;

    private Long target_Output;

    private Long now_Output;

    @Enumerated(EnumType.STRING)
    private productionPlan_state state;


    @OneToMany(mappedBy = "productPlanCode",  cascade = CascadeType.REMOVE)
    private List<workOrderPlan> workOrderPlanList;

    @OneToMany(mappedBy = "productionPlanCode",  cascade = CascadeType.REMOVE)
    private List<workPerform> workPerformList;




}
