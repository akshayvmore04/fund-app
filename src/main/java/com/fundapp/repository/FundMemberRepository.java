package com.fundapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fundapp.entity.FundMember;

public interface FundMemberRepository extends JpaRepository<FundMember, Long> {

    boolean existsByFund_IdAndUser_Id(Long fundId, Long userId);

    List<FundMember> findByFund_Id(Long fundId);

    List<FundMember> findByUser_Id(Long userId);

}
