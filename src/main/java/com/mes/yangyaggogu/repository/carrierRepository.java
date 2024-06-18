package com.mes.yangyaggogu.repository;

import com.mes.yangyaggogu.entity.carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface carrierRepository extends JpaRepository<carrier,String> {

}
