package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import com.mes.yangyaggogu.repository.obtainorder_numberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public obtainorder_detail save(AddOrderDto addOrderDto){
        return obtainorderDetailRepository.save(addOrderDto.toEntity());
    }
}
