package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.entity.finishedstock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class finishedstockService {

    final private com.mes.yangyaggogu.repository.finishedstockRepository finishedstockRepository;


    public List<finishedstock> showFinishedStockList() {
        return finishedstockRepository.findAll();
    }

    public Optional<finishedstock> findById(Long id) {
        return finishedstockRepository.findById(id);
    }

    public finishedstock save(finishedstock finishedStock) {
        return finishedstockRepository.save(finishedStock);
    }

}
