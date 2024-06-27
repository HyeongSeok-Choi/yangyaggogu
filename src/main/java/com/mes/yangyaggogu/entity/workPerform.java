package com.mes.yangyaggogu.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class workPerform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productionPlanCode")
    private productPlan productionPlanCode;

    private Float operationRate;

    private double target_Output;

    private double now_Output;

    private String processName;

    private Long defect_Rate;

}
