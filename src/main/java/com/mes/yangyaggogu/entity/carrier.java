package com.mes.yangyaggogu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class carrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carrier_Code;

    private String carrier_Name;

    private String carrier_PhoneNumber;

    private String carrier_Vehicle;

    private Long carrier_Price;

    private String carrier_manager;

    @ManyToOne
    @JoinColumn(name = "shipment_Number")
    private shipment shipment;

}
