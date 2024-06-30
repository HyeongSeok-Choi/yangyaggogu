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

    //생산자 모두 출력
    public List<employee> getProductor(){
        

        employeerepository.findByPositionName("생산");

        return employeerepository.findByPositionName("생산");

    }

    //직원 목록 모두 출력
    public List<employee> getEmployee(){


        return employeerepository.findAll();

    }

    //직원 삭제
    public void deleteEmployee(List<String> codes){

            employeerepository.deleteAllById(codes);

    }

    //직원 등록
    public employee addEmployee(employee employee){

        Long count = employeerepository.countByPositionName(employee.getPositionName()) +1;

        if(employee.getPositionName().equals("생산")){
            employee.setEmployeeCode("p-"+count);
        } else if (employee.getPositionName().equals("행정")) {
            employee.setEmployeeCode("ad-"+count);
        }else{
            employee.setEmployeeCode("pl-"+count);
        }

        return   employeerepository.save(employee);
    }

}
