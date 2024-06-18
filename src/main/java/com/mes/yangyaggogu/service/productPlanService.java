package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.repository.productPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class productPlanService {

    final private productPlanRepository productPlanRepository;

    public List<productPlan> showOrderLists() {
        return productPlanRepository.findAll();
    }
}
