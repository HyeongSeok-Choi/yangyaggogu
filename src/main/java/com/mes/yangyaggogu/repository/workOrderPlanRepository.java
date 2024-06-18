package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface workOrderPlanRepository extends JpaRepository<workOrderPlan, Long> {

    workOrderPlan findByProductPlanCodeAndProcessCode(productPlan productPlanCode, String processCode);
    
}
