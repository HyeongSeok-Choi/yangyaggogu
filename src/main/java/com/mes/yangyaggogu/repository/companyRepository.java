package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.constant.company_state;
import com.mes.yangyaggogu.entity.company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface companyRepository extends JpaRepository<company, Long> {


    @Query("SELECT c FROM company c WHERE c.company_name = :companyName and c.state = 'customer'")
    Optional<company> findByCompanyName(@Param("companyName") String companyName);
    //findByCompanyName 했는데 안돼서 걍 피티형한테 맡김 기능은같음


    @Query("select t from company t where t.state = :state And t.trade_goods LIKE %:tradeGoods%")
    List<company> findByStateAndTradeGoods(company_state state, String tradeGoods);
    List<company> findByState(company_state company_state);

}
