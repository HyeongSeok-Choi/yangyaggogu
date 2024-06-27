package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.constant.productionPlan_state;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.productPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface productPlanRepository extends JpaRepository<productPlan, String> {

    @Query("select p from productPlan p where (p.pstartDate <= :end) and (:start <= p.pendDate)")
    List<productPlan> getBestPost(LocalDate start, LocalDate end);

    @Query("select p from productPlan p where (p.pstartDate = :start) and (p.materialsName = :productName)")
    List<productPlan> getEqualStartDatePlan(LocalDate start,String productName);

    List<productPlan> findAllByState(productionPlan_state productionPlanState);

    @Query("select p from productPlan p where (p.pstartDate <= :todayDate) and (:todayDate <= p.pendDate)")
    List<productPlan> getTodayPlan(LocalDate todayDate);



    // boolean existsByPstartDateLessThanEqualAndPendDateGreaterThanEqual(LocalDate start, LocalDate end);

    //productPlan findByMaterialsNameAndOrderNumber(String name, obtainorder_number obtainorderNumber);



}
