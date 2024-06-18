package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.entity.Company;
import com.mes.yangyaggogu.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class CompanyApiController {


    final private CompanyService companyService;


    @GetMapping("/company/list")
    public Map<String,Object> showCompanies() {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",companyService.showCompanies());

        System.out.println("데이터테이블 실행 완료");

        return map;
    }






    @PostMapping("/company/delete")
    public ResponseEntity<Void> deleteCompanies(@RequestBody List<Long> company_codes) {
        companyService.deleteCompanies(company_codes);
        return ResponseEntity.ok().build();
    }
}
