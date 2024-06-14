package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.dto.OrderStateDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;

public interface ObtainorderService {

    //수주 현황 조회
    OrderStateDto read(Long id);

    //dto를 Entity로 변환
    default obtainorder_detail dtoToEntity(OrderStateDto orderStateDto) {
        obtainorder_detail entity = obtainorder_detail.builder()
                .id(orderStateDto.getId())
                .orderNumber(orderStateDto.getOrderNumber())
                .company_name(orderStateDto.getCompany_name())
                .productName(orderStateDto.getProductName())
                .order_Date(orderStateDto.getOrder_Date())
                .order_Amount(orderStateDto.getOrder_Amount())
                .delivery_Date(orderStateDto.getDelivery_Date())
                .state(orderStateDto.getState())
                .writer(orderStateDto.getWriter())
                .build();
        return entity;
    }

    //entity를 dto로 변환
    default OrderStateDto entityToDto(obtainorder_detail entity){
        OrderStateDto orderStateDto = OrderStateDto.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .company_name(entity.getCompany_name())
                .productName(entity.getProductName())
                .order_Date(entity.getOrder_Date())
                .order_Amount(entity.getOrder_Amount())
                .delivery_Date(entity.getDelivery_Date())
                .state(entity.getState())
                .writer(entity.getWriter())
                .build();
        return orderStateDto;
    }
}
