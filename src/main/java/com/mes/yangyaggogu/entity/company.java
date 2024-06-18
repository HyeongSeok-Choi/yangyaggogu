package com.mes.yangyaggogu.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class company {

    @Id
    private String company_code;

    private String company_name;

    private String company_address;

    private String company_tel_number;

    private String trade_goods;

    private LocalDateTime deliveryTime;

    private String company_manager;
}
