package com.mes.yangyaggogu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mes.yangyaggogu.constant.finishedstock_state;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class finishedstock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_Number")
    private obtainorder_number orderNumber;

    private String materials_Name;

    private Long amount;

    private LocalDate exp;

    @Enumerated(EnumType.STRING)
    private finishedstock_state state;

    private String shipmentState;

//(fetch = FetchType.LAZY)

}
