package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.dto.OrderDtlDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObtainOrderService {

    public final obtainorder_detailRepository obtainorderDetailRepository;
    public final obtainorder_numberRepository obtainOrderNumberRepository;

    //수주 현황 조회, 수주 상세 조회
    public List<obtainorder_detail> getObtainOrderDtl() {
        return obtainorderDetailRepository.findAll();
    }

    //등록
    public obtainorder_detail save(AddOrderDto addOrderDto){
        return obtainorderDetailRepository.save(addOrderDto.toEntity());
    }

    //수주 상세 조회
    public OrderDtlDto getOrderDetailById(Long id){

        obtainorder_detail findObtain=
        obtainorderDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found :" + id));

        OrderDtlDto findedDto = new OrderDtlDto(findObtain);

        return findedDto;
    }

}
