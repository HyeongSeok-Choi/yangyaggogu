package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.dto.OrderDtlDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    @Transactional
    public boolean saveList(List<AddOrderDto> addOrderDtoList){


        //수주 번호의 생성
        obtainorder_number addOrderNumber = new obtainorder_number();

        Long count = getObtainOrderNumber(addOrderDtoList.get(0).getOrder_Date());

        if(count == 0){
           count = 1L;
        }

        addOrderNumber.setOrder_Number(LocalDate.now().toString()+"-"+count);

        obtainOrderNumberRepository.save(addOrderNumber);


        //수주 디테일 저장

        for (AddOrderDto addOrderDto : addOrderDtoList) {

            obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

            obtainorder_detail.setOrderNumber(addOrderNumber);

            obtainorderDetailRepository.save(obtainorder_detail);
        }
        return true;
    }

    //수주번호 인덱스 계산 (but 실패 !! 고쳐야함 )
    public Long getObtainOrderNumber(LocalDate date){

       Long count = obtainorderDetailRepository.countByOrderDate(date);

       return count;

    }

    //수주 상세 조회
    public OrderDtlDto getOrderDetailById(Long id){

        obtainorder_detail findObtain=
        obtainorderDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found :" + id));

        OrderDtlDto findedDto = new OrderDtlDto(findObtain);

        return findedDto;
    }


    public obtainorder_detail findByOrderNumber(obtainorder_number orderNumber) {
        return obtainorderDetailRepository.findByOrderNumber(orderNumber);
    }
}
