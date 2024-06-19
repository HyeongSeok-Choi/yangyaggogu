package com.mes.yangyaggogu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class shipmentDTO {

    private String order_Number;
    private String productionName;
    private Long shipment_Amount;

}
;