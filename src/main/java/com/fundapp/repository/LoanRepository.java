package com.fundapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundapp.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByFundId(Long fundId);

    List<Loan> findByUserId(Long userId);

}
