package com.myblog.yangyaggogu.service;

import com.myblog.yangyaggogu.constant.finishedstock_state;
import com.myblog.yangyaggogu.constant.workOrderPlan_state;
import com.myblog.yangyaggogu.entity.finishedstock;
import com.myblog.yangyaggogu.entity.obtainorder_number;
import com.myblog.yangyaggogu.entity.productPlan;
import com.myblog.yangyaggogu.entity.workOrderPlan;
import com.myblog.yangyaggogu.repository.finishedstockRepository;
import com.myblog.yangyaggogu.repository.workOrderPlanRepository;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@Service
public class workOrderPlanService {


    final private workOrderPlanRepository workOrderPlanRepository;
    final private finishedstockRepository finishedStockRepository;


    public List<workOrderPlan> showWorkOrderPlan() {
        return workOrderPlanRepository.findAll();
    }


    //포장, completed면 finishedStock(완제품 재고) 로 자동 저장
    public void saveWorkOrderPlan(workOrderPlan workOrder) {
        workOrderPlanRepository.save(workOrder);
        if ("포장".equals(workOrder.getProcessName()) && workOrderPlan_state.completed.equals(workOrder.getState())) {
            LocalDateTime expDate = workOrder.getP_endDate().plusMonths(6);
            finishedstock finished = finishedstock.builder()
                    .orderNumber(workOrder.getObtainorder_number())
                    .amount(workOrder.getNow_Output())
                    .exp(expDate)
                    .materials_Name(workOrder.getMaterials_Name())
                    .state(finishedstock_state.in)
                    .build();

            finishedStockRepository.save(finished);
        }
    }
}

