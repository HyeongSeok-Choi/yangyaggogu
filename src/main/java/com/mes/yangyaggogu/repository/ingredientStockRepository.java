package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.ingredientStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ingredientStockRepository extends JpaRepository<ingredientStock, Long> {

    @Query(value = "SELECT e FROM ingredientStock e WHERE e.in_date BETWEEN :start AND :end AND e.materials_Name = :name")
    List<ingredientStock> getRowStockPage(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("name") String name);


}
