package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.constant.shipment_state;
import com.mes.yangyaggogu.entity.shipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class shipmentDTO {

    private String shipment_Number;
    private String order_Number;
    private String company_name;
    private String company_Address;
    private Long shipment_Amount;
    private String productionName;
    private LocalDateTime shippingDate;
    private LocalDate deliveryDate;
    private LocalDateTime createdAt;
    private shipment_state state;

    public shipmentDTO(shipment shipment) {
        this.shipment_Number = shipment.getShipment_Number();

        this.order_Number = shipment.getOrder_Number().getOrderNumber();
        this.company_name = shipment.getCompany_name();
        this.company_Address = shipment.getCompany_Address();
        this.shipment_Amount = shipment.getShipment_Amount();
        this.productionName = shipment.getProductionName();
        this.shippingDate = shipment.getShippingDate();
        this.deliveryDate = shipment.getDeliveryDate();
        this.createdAt = shipment.getCreatedAt();
        this.state = shipment.getState();

    }
}
;