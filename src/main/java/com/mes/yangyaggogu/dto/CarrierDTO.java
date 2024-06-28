package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.entity.carrier;
import com.mes.yangyaggogu.entity.shipment;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierDTO {

    private Long carrier_Code;

    private String carrier_Name;

    private String carrier_PhoneNumber;

    private String carrier_Vehicle;

    private Long carrier_Price;

    private String carrier_manager;

    private shipment shipment;

    public CarrierDTO(carrier carrier) {
        this.carrier_Code = carrier.getCarrier_Code();
        this.carrier_Name = carrier.getCarrier_Name();
        this.carrier_PhoneNumber = carrier.getCarrier_PhoneNumber();
        this.carrier_Vehicle = carrier.getCarrier_Vehicle();
        this.carrier_Price = carrier.getCarrier_Price();
        this.carrier_manager= carrier.getCarrier_manager();
        this.shipment = carrier.getShipment();
    }


}