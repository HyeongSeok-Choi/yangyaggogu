package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.workOrderPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface workPerformRepository extends JpaRepository<workOrderPlan, String> {
    
}
