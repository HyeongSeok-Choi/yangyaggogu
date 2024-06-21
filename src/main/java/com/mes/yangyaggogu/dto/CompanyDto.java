package com.mes.yangyaggogu.dto;

import com.mes.yangyaggogu.constant.company_state;
import com.mes.yangyaggogu.entity.company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private Long company_code;

    private String company_name;

    private String company_address;

    private String company_tel_number;

    private String trade_goods;

    private String company_manager;

    private company_state state;

    public CompanyDto(company company) {
        this.company_code = company.getCompany_code();
        this.company_name = company.getCompany_name();
        this.company_address = company.getCompany_address();
        this.company_tel_number = company.getCompany_tel_number();
        this.trade_goods = company.getTrade_goods();
        this.company_manager = company.getCompany_manager();
        this.state = company.getState();
    }
}
