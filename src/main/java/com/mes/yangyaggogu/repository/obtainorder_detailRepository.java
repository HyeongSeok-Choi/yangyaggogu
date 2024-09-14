package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.constant.obtainorder_state;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import com.mes.yangyaggogu.entity.shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface obtainorder_detailRepository extends JpaRepository<obtainorder_detail, Long> {

    long countByOrderDate(LocalDate obtainOrderDate);

    List<obtainorder_detail> findByOrderNumber(obtainorder_number orderNumber);
    //shipmentApiController에서 출하 지시서 테이블에 거래처 이름을 수주 상세 테이블에서 받아올때 사용


    Boolean existsByOrderDate(LocalDate obtainOrderDate);

    obtainorder_detail findTopByOrderByIdDesc();

    obtainorder_detail findByProductNameAndOrderNumber(String productName, obtainorder_number orderNumber);

    List<obtainorder_detail> findByState(obtainorder_state state);

    @Query(value = "SELECT e FROM obtainorder_detail e WHERE e.orderDate BETWEEN :start AND :end AND e.company_name = :name")
    List<obtainorder_detail> getFinishedStockPage(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("name") String name);

}
