package com.mes.yangyaggogu.service;

import com.mes.yangyaggogu.dto.FinishedStockDTO;
import com.mes.yangyaggogu.dto.searchDto;
import com.mes.yangyaggogu.entity.finishedstock;
import com.mes.yangyaggogu.repository.finishedstockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class finishedstockService {

    private final finishedstockRepository  finishedstockRepository;


    public List<finishedstock> showFinishedStockList() {
        return finishedstockRepository.findAll();
    }

    public Optional<finishedstock> findById(Long id) {
        return finishedstockRepository.findById(id);
    }

    public finishedstock save(finishedstock finishedStock) {
        return finishedstockRepository.save(finishedStock);
    }

    public List<FinishedStockDTO> searchLists(searchDto searchDto){


        List<FinishedStockDTO> searchLists = finishedstockRepository.getFinishedStockPage(searchDto.getStart(), searchDto.getEnd(), searchDto.getKeyword())
        .stream()
                .map(a -> new FinishedStockDTO(a))
                .collect(Collectors.toList());

        return searchLists;
    }


}
