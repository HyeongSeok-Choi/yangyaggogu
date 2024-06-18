package com.mes.yangyaggogu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class carrier {

    @Id
    private String carrier_Code;

    private String carrier_Name;

    private String carrier_PhoneNumber;

    private String carrier_Vehicle;

    private Long carrier_Price;

    private String carrier_manager;

}
