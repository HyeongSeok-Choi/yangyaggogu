package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.entity.carrier;
import com.mes.yangyaggogu.entity.company;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.repository.carrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class carrierService {

    final private carrierRepository carrierRepository;

    public void registerCarrier(carrier carrier) {
        carrierRepository.save(carrier);
    }


    public carrier findByShipment(shipment shipment) {
        return carrierRepository.findByShipment(shipment);

    }

}
