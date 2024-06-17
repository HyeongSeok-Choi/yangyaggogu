package com.myblog.yangyaggogu.entity;

import com.myblog.yangyaggogu.constant.finishedstock_state;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class finishedstock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
