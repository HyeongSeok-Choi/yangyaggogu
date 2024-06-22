package com.mes.yangyaggogu.constant;

import lombok.Getter;

@Getter
public enum company_state {

    customer("고객사"),
    delivery("거래처");

    private final String value;
    company_state( String value) {
        this.value = value;
    }
}
