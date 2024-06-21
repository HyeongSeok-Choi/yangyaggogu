package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.service.finishedstockService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class finishedStockController {

    private final finishedstockService finishedstockService;


    @GetMapping("/order_list")
    public String showOrderLists() { return "finishedStock/orderConfirm";}


    @GetMapping("/finishedStock/list")
    public String showFinishedStockList(Model model, searchDto searchDto)  {

        finishedstockService.showFinishedStockList();
        model.addAttribute("searchDto", searchDto);
        return "/finishedStock/finishedStock";
    }
}
