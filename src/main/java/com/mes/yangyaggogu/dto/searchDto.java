package com.mes.yangyaggogu.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data
public class searchDto {

    private LocalDate start;
    private LocalDate end;
    private String keyword;
}
