package com.mes.yangyaggogu.controller;


import com.mes.yangyaggogu.constant.company_state;
import com.mes.yangyaggogu.dto.CompanyDto;
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
    public String showRegistCompanyForm(Model model) {

        model.addAttribute("companyDto", new CompanyDto());
        return "company/registCompany";
    }


//    @ModelAttribute("companyState")
//    public company_state[] companyState() {
//        return company_state.values();
//    }
    @PostMapping("/company/register")
    public String registerCompany(@ModelAttribute company company,
                                  RedirectAttributes redirectAttributes,CompanyDto companyDto) {

            company.setState(companyDto.getState());
            companyService.registCompany(company);
            redirectAttributes.addFlashAttribute("message", "거래처가 성공적으로 등록되었습니다.");

        return "redirect:/company/list";
    }


    @GetMapping("/company/list")
    public String showCompanyList() {
        return "company/showCompanyList";
    }


}
