package com.mes.yangyaggogu.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mes.yangyaggogu.constant.obtainorder_state;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class obtainorder_number {

    @Id
    private String order_Number;

    @OneToMany(mappedBy = "order_Number",  cascade = CascadeType.REMOVE)
    private List<productPlan> productPlanList;

}
