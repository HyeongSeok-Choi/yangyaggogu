package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.productPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface productPlanRepository extends JpaRepository<productPlan, String> {

    @Query("select p from productPlan p where (p.pstartDate <= :end) and (:start <= p.pendDate)")
    List<productPlan> getBestPost(LocalDate start, LocalDate end);

   // boolean existsByPstartDateLessThanEqualAndPendDateGreaterThanEqual(LocalDate start, LocalDate end);
    
}
