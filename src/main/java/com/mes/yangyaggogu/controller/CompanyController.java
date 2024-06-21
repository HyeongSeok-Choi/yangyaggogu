package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.entity.company;
import com.mes.yangyaggogu.entity.shipment;
import com.mes.yangyaggogu.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
public class CompanyController {

    final private CompanyService companyService;

    @GetMapping("/company/regist")
    public String showRegistCompanyForm() {
        return "company/registCompany";
    }

    @PostMapping("/company/register")
    public String registerCompany(@ModelAttribute company company,
                                  RedirectAttributes redirectAttributes) {
                companyService.registCompany(company);
                redirectAttributes.addFlashAttribute("message", "거래처가 성공적으로 등록되었습니다.");

        return "redirect:/company/list";
    }


    @GetMapping("/company/list")
    public String showCompanyList() {
        return "company/showCompanyList";
    }


}
