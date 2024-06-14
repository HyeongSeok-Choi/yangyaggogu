package com.myblog.yangyaggogu.controller;

import com.myblog.yangyaggogu.entity.Company;
import com.myblog.yangyaggogu.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
public class CompanyController {

    final private CompanyService companyService;

    @GetMapping("/company/regist")
    public String showRegistCompanyForm() {
        return "company/registCompany";
    }

    @PostMapping("/company/register")
    public String registerCompany(@ModelAttribute Company company,

                                                               RedirectAttributes redirectAttributes) {
                companyService.registCompany(company);
                redirectAttributes.addFlashAttribute("message", "거래처가 성공적으로 등록되었습니다.");

        return "main";
    }


    @GetMapping("/company/list")
    public String showCompanyList() {
        return "company/showCompanyList";
    }




}
