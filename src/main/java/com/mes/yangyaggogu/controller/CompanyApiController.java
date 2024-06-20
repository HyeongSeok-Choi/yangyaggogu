package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.dto.CompanyDto;
import com.mes.yangyaggogu.dto.FinishedStockDTO;
import com.mes.yangyaggogu.entity.company;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping(value = "/api")
@RestController
@RequiredArgsConstructor
public class CompanyApiController {


    final private CompanyService companyService;


    @GetMapping("/company/list")
    public Map<String,Object> showCompanies() {

        Map<String,Object> map = new HashMap<String,Object>();

        List<CompanyDto> companyDtoList = companyService.showCompanies().stream()
                .map(a -> new CompanyDto(a))
                .collect(Collectors.toList());
        map.put("data",companyDtoList);

        System.out.println("데이터테이블 실행 완료");

        return map;
    }
    @PostMapping("/company/delete")
    public ResponseEntity<Void> deleteCompanies(@RequestBody List<Long> company_codes) {
        companyService.deleteCompanies(company_codes);
        return ResponseEntity.ok().build();
    }

    //거래처 이름 가져오기
    @GetMapping("/companies/name")
    public List<String> getCompanies() {
        return companyService.getAllCompanyNames();
    }



}
