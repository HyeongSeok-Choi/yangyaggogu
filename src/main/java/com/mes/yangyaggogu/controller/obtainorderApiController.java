package com.mes.yangyaggogu.controller;

import com.mes.yangyaggogu.dto.OrderStateDto;
import com.mes.yangyaggogu.entity.obtainorder_detail;
import com.mes.yangyaggogu.service.ObtainorderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class obtainorderApiController {

    private final ObtainorderService obtainorderService;

    /*@GetMapping("/api/obtainorder/{id}")
    public ResponseEntity<OrderStateDto> findOrder(@PathVariable long id){
        obtainorder_detail obtainorderDetail = obtainorderService.findById
    }*/
}
