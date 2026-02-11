package com.fundapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundapp.entity.LoanEmi;

public interface LoanEmiRepository extends JpaRepository<LoanEmi, Long> {

    boolean existsByLoanIdAndMonth(Long loanId, String month);

    List<LoanEmi> findByLoanId(Long loanId);

}
