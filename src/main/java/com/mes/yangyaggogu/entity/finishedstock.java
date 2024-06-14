package com.mes.yangyaggogu.entity;

import com.mes.yangyaggogu.constant.finishedstock_state;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class finishedstock {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_Number")
    private obtainorder_number orderNumber;

    private String materials_Name;

    private Long amount;

    private LocalDateTime exp;

    private finishedstock_state state;

}
