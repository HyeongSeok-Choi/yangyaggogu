package com.mes.yangyaggogu.entity;

import com.mes.yangyaggogu.constant.equipment_state;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class equipment {

    @Id
    private String EquipmentCode;

    private String EquipmentName;

    private Long operationRate;

    private String ReasonNoOp;

    private equipment_state state;

    private LocalDate settingDate;


}
