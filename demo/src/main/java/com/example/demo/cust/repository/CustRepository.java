package com.example.demo.cust.repository;

import com.example.demo.cust.entity.CustEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustRepository extends JpaRepository<CustEntity, Long> {
}
