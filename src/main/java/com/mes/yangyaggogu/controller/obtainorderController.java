package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.OrderStateDto;
import com.mes.yangyaggogu.service.ObtainorderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@Controller
public class obtainorderController {

    private final ObtainorderService obtainorderService;
    @GetMapping("/orderstate")
    public String orderState() {
        return "obtainorder/OrderState";
    }

    //수주현황 조회
    @GetMapping("/orderstatus")
    public void orderStatus(long id, Model model){
        System.out.println("id: " + id);

        OrderStateDto orderStateDto = obtainorderService.read(id);

        model.addAttribute("dto", orderStateDto);
    }

    //수주 등록 페이지 띄우기(임시)
    @GetMapping("/order_reg")
    public String orderReg(){
        return "obtainorder/OrderRegistration";
    }
}
