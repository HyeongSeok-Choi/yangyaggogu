package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface obtainorder_detailRepository extends JpaRepository<obtainorder_detail, Long> {

    long countByOrderDate(LocalDate obtainOrderDate);

    List<obtainorder_detail> findByOrderNumber(obtainorder_number orderNumber);
    //shipmentApiController에서 출하 지시서 테이블에 거래처 이름을 수주 상세 테이블에서 받아올때 사용


    Boolean existsByOrderDate(LocalDate obtainOrderDate);

    obtainorder_detail findTopByOrderByIdDesc();

    obtainorder_detail findByProductNameAndOrderNumber(String productName, obtainorder_number orderNumber);

}
