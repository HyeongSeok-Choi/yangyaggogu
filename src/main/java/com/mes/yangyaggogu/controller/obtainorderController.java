package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.AddOrderDto;
import com.mes.yangyaggogu.dto.OrderDtlDto;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.service.ObtainOrderService;
import com.mes.yangyaggogu.service.employeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class obtainorderController {

    private final employeeService empService;
    private final ObtainOrderService obtainOrderService;
    @GetMapping("/orderstate")
    public String orderState() {

        return "obtainorder/OrderState";
    }

    //캘린더 팝업창 띄우기
    @GetMapping("/popup.calendar")
    public String popup_calendar() {
        return "productionPlan/popup_calendar";
    }

    //수주 상세 조회
    @GetMapping("/order_Detail/{id}")
    public String popup_order_detail(Model model,@PathVariable Long id) {
        OrderDtlDto orderDtlDto =obtainOrderService.getOrderDetailById(id);
        model.addAttribute("orderDtlDto", orderDtlDto);

        return "obtainorder/OrderDetail";
    }



    //수주 등록 페이지 띄우기(임시)
    @GetMapping("/order_reg")
    public String orderReg(@RequestParam(required = false) Long id, Model model){

        if(empService.getEmpByPosition("행정")!= null){
         model.addAttribute("emp",empService.getEmpByPosition("행정"));
        }

        if (id == null) {
            model.addAttribute("obtainorderDetail", new AddOrderDto());

        }
        return "obtainorder/OrderRegistration";
    }


    @GetMapping("/completed-orders")
    public String showOrdersPage(Model model, searchDto searchDto) {

        model.addAttribute("searchDto" ,searchDto);
        return "company/companyOrderAmount";
    }
}
