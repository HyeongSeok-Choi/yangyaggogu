package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.obtainorder_state;
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

        int count = getObtainOrderNumber(addOrderDtoList.get(0).getOrder_Date());


        addOrderNumber.setOrder_Number(LocalDate.now().toString()+"-"+count);

        obtainOrderNumberRepository.save(addOrderNumber);


        //수주 디테일 저장
        for (AddOrderDto addOrderDto : addOrderDtoList) {

            obtainorder_detail obtainorder_detail= addOrderDto.toEntity();

            obtainorder_detail.setOrderNumber(addOrderNumber);
            obtainorder_detail.setState(obtainorder_state.ready);

            obtainorderDetailRepository.save(obtainorder_detail);
        }
        return true;
    }

    //수주번호 인덱스 계산
    public int getObtainOrderNumber(LocalDate date){

        int addNumber = 0;

        //날짜가 있는지 확인
       boolean checkLocalDate = obtainorderDetailRepository.existsByOrderDate(date);

       //날짜가 존재한다면
       if(checkLocalDate){

          obtainorder_detail findObtain = obtainorderDetailRepository.findTopByOrderByIdDesc();

          obtainorder_number obtainorderNumber = findObtain.getOrderNumber();

           System.out.println(obtainorderNumber.getOrder_Number());

          String[] num  = obtainorderNumber.getOrder_Number().split("-");

           addNumber = Integer.parseInt(num[3]);

          return addNumber+1;

       }else{
           addNumber = 1;

       }

       return addNumber;

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
