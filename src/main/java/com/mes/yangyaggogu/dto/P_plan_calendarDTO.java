package com.mes.yangyaggogu.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class P_plan_calendarDTO {

    private LocalDate start;
    private LocalDate end;
    private String title;
    private String color;

}
