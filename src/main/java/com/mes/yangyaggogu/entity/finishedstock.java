package com.mes.yangyaggogu.entity;

import com.mes.yangyaggogu.constant.finishedstock_state;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private finishedstock_state state;

}
