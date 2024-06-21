package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.constant.finishedstock_state;
import com.mes.yangyaggogu.entity.finishedstock;
import com.mes.yangyaggogu.entity.obtainorder_number;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishedStockDTO {

    private Long id;

    private String orderNumber;

    private String materials_Name;

    private Long amount;

    private LocalDate exp;

    private finishedstock_state state;

    private String shipmentState;

    public FinishedStockDTO(finishedstock finishedstock) {
        this.id = finishedstock.getId();
        this.orderNumber = finishedstock.getOrderNumber().getOrder_Number();
        this.materials_Name = finishedstock.getMaterials_Name();
        this.amount = finishedstock.getAmount();
        this.exp = finishedstock.getExp();
        this.state = finishedstock.getState();
        this.shipmentState = finishedstock.getShipmentState();

    }

}