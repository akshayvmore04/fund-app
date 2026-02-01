package com.fundapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundapp.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByFundId(Long fundId);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByFundIdAndMonth(Long fundId, String month);

}
