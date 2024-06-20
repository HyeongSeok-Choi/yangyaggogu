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

    private Long target_Output;

    private Long now_Output;

    private String processName;

    private Long defect_Rate;

}
