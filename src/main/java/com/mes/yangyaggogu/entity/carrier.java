package com.mes.yangyaggogu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_Number")
    private shipment shipment;

}
