package com.mes.yangyaggogu.service;


import com.mes.yangyaggogu.entity.company;

import com.mes.yangyaggogu.repository.companyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CompanyService {

    final private companyRepository companyRepository;

    private final Map<String, Integer> sequenceMap = new HashMap<>();

    public void registCompany(company company) {

//        String companyCode = generateCompanyCode();
//
//        company.setCompany_code(companyCode);
        companyRepository.save(company);
    }

    public List<company> showCompanies() {
        return companyRepository.findAll();
    }




    //거래처 코드 어떻게 짤지 정하고 나중에 수정
//    public String generateCompanyCode() {
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String datePart = today.format(formatter);
//
//        int sequenceNumber = sequenceMap.getOrDefault(datePart, 0) + 1;
//        sequenceMap.put(datePart, sequenceNumber);
//
//        return String.format("%s-%03d", datePart, sequenceNumber);
//
//    }

    public void deleteCompanies(List<Long> company_codes) {
        companyRepository.deleteAllById(company_codes);
    }
}
