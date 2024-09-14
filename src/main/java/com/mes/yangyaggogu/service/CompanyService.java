package com.mes.yangyaggogu.service;


import com.mes.yangyaggogu.constant.company_state;
import com.mes.yangyaggogu.entity.carrier;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyService {

    final private companyRepository companyRepository;

    private final Map<String, Integer> sequenceMap = new HashMap<>();

    public company registCompany(company company) {
       return companyRepository.save(company);
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

    //거래처 주요 거래품에 따른 리스트 보여주기
    public List<String> findByTradeGoods(company_state state, String tradeGoods){

        return companyRepository.findByStateAndTradeGoods(state,tradeGoods).stream()
                .map(company :: getCompany_name)
                .collect(Collectors.toList());
    }

    //거래처 코드 어떻게 짤지 정하고 나중에 수정

    //거래처 이름 가져오기(진영)
    //거래처 중 고객사만 가져오게 바꿈 (형석)
    public List<String> getAllCompanyNames(company_state state){

        return companyRepository.findByState(state).stream()
                .map(company ::getCompany_name)
                .collect(Collectors.toList());
    }
}
