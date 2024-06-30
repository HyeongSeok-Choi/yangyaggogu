package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.entity.employee;
import com.mes.yangyaggogu.service.employeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class RefereceInfoAPIController {

    private final employeeService empService;

    //사원목록 출력
    @GetMapping("/GetEmpList")
    public ResponseEntity<Map<String,Object>> GetEmpList() {

        Map<String,Object> data= new HashMap<>();

        List<employee> employeeList = empService.getEmployee();

        data.put("data",employeeList);


        return ResponseEntity.ok(data);
    }


    //사원 등록
    @PostMapping("/emp/register")
    public ResponseEntity<employee> registerCompany(@RequestBody employee employee) {

        employee savedCompany = empService.addEmployee(employee);

        return ResponseEntity.ok(savedCompany);
    }

    @PostMapping("/emp/delete")
    public ResponseEntity<Void> deleteEmp(@RequestBody List<String> emp_codes) {
        empService.deleteEmployee(emp_codes);
        return ResponseEntity.ok().build();
    }

}
