package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.dto.StockDto;
import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.productPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ingredientStockRepository extends JpaRepository<ingredientStock, Long> {

    @Query(value = "SELECT e FROM ingredientStock e WHERE e.in_date BETWEEN :start AND :end AND e.materials_Name = :name")
    List<ingredientStock> getRowStockPage(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("name") String name);

    Optional<ingredientStock> findAllById(Long id);

    ingredientStock findByProductionPlanCode(productPlan productionPlanCode);

    @Query(value = "select o from ingredientStock o where o.order_date =:orderDate And o.materials_Name =:name And o.company_name =:companyName")
    List<ingredientStock> getByOrder_dateAndIngredient_Amount(LocalDate orderDate, String name , String companyName);

}
