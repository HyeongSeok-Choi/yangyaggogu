package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.entity.Company;
import com.mes.yangyaggogu.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {

    final private CompanyRepository companyRepository;

    public void registCompany(Company company) {
        companyRepository.save(company);
    }

    public List<Company> showCompanies() {
        return companyRepository.findAll();
    }


    public void deleteCompanies(List<Long> company_codes) {
        companyRepository.deleteAllById(company_codes);
    }
}
