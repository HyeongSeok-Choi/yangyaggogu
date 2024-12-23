package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface employeeRepository extends JpaRepository<employee, String> {

    List<employee> findByPositionName(String positionName);

    Long countByPositionName(String positionName);


}
