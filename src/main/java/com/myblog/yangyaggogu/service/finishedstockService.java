package com.myblog.yangyaggogu.service;

import com.myblog.yangyaggogu.constant.finishedstock_state;
import com.myblog.yangyaggogu.entity.finishedstock;
import com.myblog.yangyaggogu.entity.workOrderPlan;
import com.myblog.yangyaggogu.repository.finishedstockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class finishedstockService {

    final private finishedstockRepository finishedstockRepository;


    public List<finishedstock> showWorkOrderPlan() {
        return finishedstockRepository.findAll();
    }

    public Optional<finishedstock> findById(Long id) {
        return finishedstockRepository.findById(id);
    }

    public finishedstock save(finishedstock finishedStock) {
        return finishedstockRepository.save(finishedStock);
    }

}
