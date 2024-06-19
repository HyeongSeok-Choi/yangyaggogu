package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface companyRepository extends JpaRepository<company, Long> {


    @Query("SELECT c FROM company c WHERE c.company_name = :companyName")
    Optional<company> findByCompanyName(@Param("companyName") String companyName);
}
