package com.myblog.yangyaggogu.service;

import com.myblog.yangyaggogu.entity.Company;
import com.myblog.yangyaggogu.entity.productPlan;
import com.myblog.yangyaggogu.repository.productPlanRepository;
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
