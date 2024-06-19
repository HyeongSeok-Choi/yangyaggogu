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
        

        employeerepository.findByPositionName("생산자");

        return employeerepository.findByPositionName("생산자");

    }



}
