package com.myblog.yangyaggogu.service;

import com.myblog.yangyaggogu.entity.Company;
import com.myblog.yangyaggogu.repository.CompanyRepository;
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
