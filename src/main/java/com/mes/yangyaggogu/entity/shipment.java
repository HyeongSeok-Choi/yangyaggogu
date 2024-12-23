package com.mes.yangyaggogu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mes.yangyaggogu.constant.shipment_state;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class shipment {

    @Id
    private String shipment_Number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderNumber")
    private obtainorder_number order_Number;

    private String company_name;

    private String company_Address;

    private Long shipment_Amount;

    private String productionName;

    private LocalDateTime shippingDate;


    private LocalDate deliveryDate;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private shipment_state state;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<carrier> carriers;
}
