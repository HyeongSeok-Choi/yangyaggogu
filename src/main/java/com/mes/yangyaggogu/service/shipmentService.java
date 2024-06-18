package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.dto.shipmentDTO;
import com.mes.yangyaggogu.entity.finishedstock;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.entity.workOrderPlan;
import com.mes.yangyaggogu.repository.finishedstockRepository;
import com.mes.yangyaggogu.repository.shipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class shipmentService {


    final private shipmentRepository shipmentRepository;
    private final Map<String, Integer> sequenceMap = new HashMap<>();



    public List<shipment> showShipmentList() {
        return shipmentRepository.findAll();
    }

    //출하지시서 번호 생성기 ex)20240618-001
    public String generateShipmentNumber() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        int sequenceNumber = sequenceMap.getOrDefault(datePart, 0) + 1;
        sequenceMap.put(datePart, sequenceNumber);

        return String.format("%s-%03d", datePart, sequenceNumber);

    }

    public void save(shipment shipment) {
        shipmentRepository.save(shipment);
    }


    public shipment findById(String id) {
        return shipmentRepository.findById(id).orElse(null);
    }
}
