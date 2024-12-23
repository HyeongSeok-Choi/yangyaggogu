package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.company_state;
import jakarta.persistence.*;
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
    @Column(name = "company_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long company_code;

    private String company_name;

    private String company_address;

    private String company_tel_number;

    private String trade_goods;

    private String company_manager;

    @Enumerated(EnumType.STRING)
    private company_state state;

}