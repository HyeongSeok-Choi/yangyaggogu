package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.ingredientStock;
import com.mes.yangyaggogu.entity.wrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface wrapRepository extends JpaRepository<wrap, Long> {

    @Query(value = "select o from wrap o where o.order_date =:orderDate And o.subMaterialsName =:name And o.companyName =:companyName")
    List<wrap> getCompanyList(LocalDate orderDate, String name , String companyName);

}
