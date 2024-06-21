package com.mes.yangyaggogu.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mes.yangyaggogu.constant.obtainorder_state;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class obtainorder_number {

    @Id
    private String orderNumber;

    @OneToMany(mappedBy = "orderNumber", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<productPlan> productPlanList;

//    @OneToMany(mappedBy = "orderNumber",  cascade = CascadeType.REMOVE)
//    private List<finishedstock> finishedstockList;

}
