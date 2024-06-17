package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.workPerform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ingredientStockRepository extends JpaRepository<ingredientStock, Long> {
    
}
