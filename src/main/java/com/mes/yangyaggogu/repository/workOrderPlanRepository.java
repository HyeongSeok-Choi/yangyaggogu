package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.productPlan;
import com.mes.yangyaggogu.entity.workOrderPlan;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface workOrderPlanRepository extends JpaRepository<workOrderPlan, Long> {

    workOrderPlan findByProductPlanCodeAndProcessCode(productPlan productPlanCode, String processCode);

    workOrderPlan findByProductPlanCode(productPlan productPlanCode);

    @Query(value = "select w.now_Output from workOrderPlan w where DATE_FORMAT( w.P_endDate,'%Y-%m-%d') =:endDate And  w.processCode =:processCode And w.materials_Name =:productName")
    List<Long> getWorkOrderPlanToday(LocalDate endDate, String processCode, String productName);

}
