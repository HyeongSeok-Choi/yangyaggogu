package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.entity.finishedstock;
import com.mes.yangyaggogu.repository.finishedstockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class productStockService {

    private final finishedstockRepository finishedStockRepository;

    public List<finishedstock> getFinishedStockList(){

        return finishedStockRepository.findAll();

    }
}
