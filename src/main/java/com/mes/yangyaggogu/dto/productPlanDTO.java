package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class productPlanDTO {

    private String productionPlanCode;

    private String materials_Name;

    private String order_Number;

    private LocalDate P_startDate;

    private LocalDate P_endDate;

    private Long target_Output;

    private Long now_Output;

    private productionPlan_state state;

    public productPlanDTO(productPlan productPlan){

        this.productionPlanCode = productPlan.getProductionPlanCode();
        this.materials_Name = productPlan.getMaterialsName();
        this.order_Number = productPlan.getOrderNumber().getOrderNumber();
        this.P_startDate = productPlan.getPstartDate();
        this.P_endDate = productPlan.getPendDate();
        this.target_Output = productPlan.getTarget_Output();
        this.now_Output = productPlan.getNow_Output();
        this.state = productPlan.getState();

    }
}
