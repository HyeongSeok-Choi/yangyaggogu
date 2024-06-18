package com.mes.yangyaggogu.service;


import com.mes.yangyaggogu.entity.employee;
import com.mes.yangyaggogu.repository.employeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class employeeService {

    private final employeeRepository employeerepository;
    
    public List<employee> getProductor(){

        //insertEmployee();

        employeerepository.findByPositionName("생산자");

        return employeerepository.findByPositionName("생산자");

    }
    
    //임시 데이터
    public void insertEmployee() {
        for (int i = 0; i < 10; i++) {
            employee emp = new employee();
            emp.setEmployeeCode("p-"+i);
            emp.setEmployeeName("안드로이드"+i);
            emp.setPositionName("생산자");

            employeerepository.save(emp);

        }
    }



}
