package com.mes.yangyaggogu.entity;


import com.mes.yangyaggogu.constant.obtainorder_state;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class obtainorder_number {

    @Id
    private String order_Number;

}
