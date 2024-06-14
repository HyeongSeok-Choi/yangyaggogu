package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.shipment_state;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class shipment {

    @Id
    private String shipment_Number;

    @ManyToOne
    @JoinColumn(name = "productionPlanCode")
    private obtainorder order_Number;

    private String company_name;

    private String company_Address;

    private String company_code;

    private Long shipment_Amount;

    private String productionName;

    private LocalDateTime shippingDate;

    private LocalDateTime deliveryDate;

    private LocalDateTime createdAt;

    private shipment_state state;

}