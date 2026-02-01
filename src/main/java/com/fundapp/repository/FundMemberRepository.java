package com.fundapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundapp.entity.FundMember;

public interface FundMemberRepository extends JpaRepository<FundMember, Long> {

    List<FundMember> findByFundId(Long fundId);

    List<FundMember> findByUserId(Long userId);

}
