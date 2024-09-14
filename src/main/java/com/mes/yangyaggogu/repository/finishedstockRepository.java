package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.finishedstock;
import com.mes.yangyaggogu.entity.ingredientStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface finishedstockRepository extends JpaRepository<finishedstock, Long> {

    @Query(value = "SELECT e FROM finishedstock e WHERE e.exp BETWEEN :start AND :end AND e.materials_Name = :name")
    List<finishedstock> getFinishedStockPage(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("name") String name);
    
}
