package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.finishedstock;
import com.mes.yangyaggogu.entity.shipment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface shipmentRepository extends JpaRepository<shipment, String> {

    @Query(value = "SELECT e FROM shipment e WHERE e.deliveryDate BETWEEN :start AND :end AND e.productionName = :name")
    List<shipment> getFinishedStockPage(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("name") String name);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM shipment s WHERE s.shipment_Number = :shipmentNumber")
    boolean existsByShipmentNumber(@Param("shipmentNumber") String shipmentNumber);

    //납품일 기준 검색 중

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM shipment s WHERE s.shipment_Number IN :shipmentNumbers")
//    void deleteByShipmentNumbers(@Param("shipmentNumbers") List<String> shipmentNumbers);
}
