package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface companyRepository extends JpaRepository<company, Long> {

public interface companyRepository extends JpaRepository<company, Long> {


    @Query("SELECT c FROM company c WHERE c.company_name = :companyName")
    Optional<company> findByCompanyName(@Param("companyName") String companyName);
    //findByCompanyName 했는데 안돼서 걍 피티형한테 맡김 기능은같음
}
