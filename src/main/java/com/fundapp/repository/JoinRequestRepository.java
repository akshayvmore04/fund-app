package com.fundapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundapp.entity.JoinRequest;

public interface JoinRequestRepository
                extends JpaRepository<JoinRequest, Long> {

        List<JoinRequest> findByFundIdAndStatus(
                        Long fundId,
                        String status);

        boolean existsByFundIdAndUserIdAndStatus(
                        Long fundId,
                        Long userId,
                        String status);

        List<JoinRequest> findByStatus(String status);

}