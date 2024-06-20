package com.mes.yangyaggogu.service;


import com.mes.yangyaggogu.entity.company;

import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.repository.companyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyService {

    final private companyRepository companyRepository;

    private final Map<String, Integer> sequenceMap = new HashMap<>();

    public void registCompany(company company) {


        companyRepository.save(company);
    }

    public List<company> showCompanies() {
        return companyRepository.findAll();
    }

    public void deleteCompanies(List<Long> company_codes) {
        companyRepository.deleteAllById(company_codes);
    }


    public Optional<company> findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
        //수주상세 테이블에서 받아온 거래처 이름으로 찾기
    }






    //거래처 코드 어떻게 짤지 정하고 나중에 수정


    public void deleteCompanies(List<Long> company_codes) {
        companyRepository.deleteAllById(company_codes);
    }
}
