package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.dto.OrderStateDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.repository.obtainorder_detailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObtainorderServiceImpl implements ObtainorderService{

    private final obtainorder_detailRepository obtainorderDetailRepository;

    //수주현황 조회
    @Override
    public OrderStateDto read(Long id){
        Optional<obtainorder_detail> readResult = obtainorderDetailRepository.findById(id);
        return readResult.isPresent()? entityToDto(readResult.get()) : null;
    }

}
