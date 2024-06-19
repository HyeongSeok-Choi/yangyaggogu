package com.mes.yangyaggogu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ingredientStock {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materials_Name;

    private String ingredient_Code;

    private Long ingredient_Amount;

    private LocalDate exp;

    private String reason;

    private Long avaliable_Amount;

    private Long out_Amount;

    @JoinColumn(name = "order_Number")
    @ManyToOne
    private obtainorder_number order_Number;

    private String company_code;

    private String company_name;

    private LocalDate in_date;


}
