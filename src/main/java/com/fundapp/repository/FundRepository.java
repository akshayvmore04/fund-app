package com.fundapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundapp.entity.Fund;

public interface FundRepository extends JpaRepository<Fund, Long> {

}
