package com.mes.yangyaggogu.entity;



import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name="company")
@ToString
@NoArgsConstructor
public class Company {

    @Id
    @Column(name = "company_code")
    private String company_code;
    private String company_name;
    private String company_address;
    private String company_tel_number;
    private String trade_goods;
    private String deliveryTime;
    private String company_manager;


    public Company(String company_name, String company_tel_number, String company_manager, String company_address, String trade_goods) {
        this.company_name = company_name;
        this.company_tel_number = company_tel_number;
        this.company_manager = company_manager;
        this.company_address = company_address;
        this.trade_goods = trade_goods;
    }
}


