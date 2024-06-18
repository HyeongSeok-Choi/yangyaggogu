package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.entity.obtainorder_number;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface obtainorder_detailRepository extends JpaRepository<obtainorder_detail, Long> {

    long countByOrderDate(LocalDate obtainOrderDate);
}
