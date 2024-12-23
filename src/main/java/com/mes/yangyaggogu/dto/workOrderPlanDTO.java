package com.mes.yangyaggogu.dto;


import com.mes.yangyaggogu.constant.workOrderPlan_state;
import com.mes.yangyaggogu.entity.workOrderPlan;
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
public class workOrderPlanDTO {


    private Long Id;

    private String processCode;

    private String productPlanCode;

    private String obtainorder_number;

    private String producer;

    private String processName;

    private LocalDateTime P_startDate;

    private LocalDateTime P_endDate;

    private double target_Output;

    private double now_Output;

    private workOrderPlan_state state;

    private String materials_Name;

    private double operationRate;

    public workOrderPlanDTO(workOrderPlan workOrderPlan) {

        this.Id = workOrderPlan.getId();
        this.processCode = workOrderPlan.getProcessCode();
        this.productPlanCode = workOrderPlan.getProductPlanCode().getProductionPlanCode();
        this.obtainorder_number = workOrderPlan.getObtainorder_number().getOrderNumber();
        this.producer = workOrderPlan.getProducer();
        this.processName = workOrderPlan.getProcessName();
        this.P_startDate = workOrderPlan.getP_startDate();
        this.P_endDate = workOrderPlan.getP_endDate();
        this.target_Output = workOrderPlan.getTarget_Output();
        this.now_Output = workOrderPlan.getNow_Output();
        this.state = workOrderPlan.getState();
        this.materials_Name = workOrderPlan.getMaterials_Name();
        this.operationRate = workOrderPlan.getOperationRate();

    }
}
