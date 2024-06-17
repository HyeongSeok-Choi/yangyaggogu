package com.myblog.yangyaggogu.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class finishedStockController {

    @GetMapping("/order_list")
    public String showOrderLists() { return "finishedStock/orderConfirm";}


    @GetMapping("finishedStock/list")
    public String showFinishedStockList()  {
        return "finishedStock/finishedStock";
    }
}
