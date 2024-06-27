package com.mes.yangyaggogu.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mes.yangyaggogu.constant.productionPlan_state;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class productPlan {

    @Id
    private String productionPlanCode;

    private String materialsName;

//
    @ManyToOne
    @JoinColumn(name = "orderNumber")
    private obtainorder_number orderNumber;

    private LocalDate pstartDate;

    private LocalDate pendDate;

    private Long target_Output;


    private Long now_Output;

    @Enumerated(EnumType.STRING)
    private productionPlan_state state;


    @OneToMany(mappedBy = "productPlanCode",fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    private List<workOrderPlan> workOrderPlanList;

    @OneToMany(mappedBy = "productionPlanCode",fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    private List<workPerform> workPerformList;

//    @JsonBackReference
    @OneToMany(mappedBy = "productionPlanCode",fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    private List<ingredientStock> productPlanList;


}
