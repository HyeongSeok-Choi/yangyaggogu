package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ObtainOrderService {

    public final obtainorder_detailRepository obtainorderDetailRepository;
    public final obtainorder_numberRepository obtainOrderNumberRepository;

    //조회
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



    public Long getObtainOrderNumber(LocalDate date){

       Long count = obtainorderDetailRepository.countByOrderDate(date);

       return count;

    }
}
