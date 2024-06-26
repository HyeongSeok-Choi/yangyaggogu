package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.constant.equipment_state;
import com.mes.yangyaggogu.entity.equipment;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class equipmentDTO {

    private String EquipmentCode;

    private String EquipmentName;

    private Long operationRate;

    private String ReasonNoOp;

    private equipment_state state;

    private LocalDate settingDate;


    public equipmentDTO(equipment equipment) {

        this.EquipmentCode = equipment.getEquipmentCode();
        this.EquipmentName = equipment.getEquipmentName();
        this.operationRate = equipment.getOperationRate();
        this.ReasonNoOp = equipment.getReasonNoOp();
        this.state = equipment.getState();
        this.settingDate = equipment.getSettingDate();

    }

}
