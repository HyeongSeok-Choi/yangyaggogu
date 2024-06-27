package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.carrier;
import com.mes.yangyaggogu.entity.shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface carrierRepository extends JpaRepository<carrier,Long> {

    carrier findByShipment(shipment shipment);

//    carrier deleteAllById(carrier carrier);

}
