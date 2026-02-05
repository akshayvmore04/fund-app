package com.fundapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundapp.dto.CreateFundRequest;
import com.fundapp.entity.Fund;
import com.fundapp.entity.FundMember;
import com.fundapp.entity.User;
import com.fundapp.repository.FundMemberRepository;
import com.fundapp.repository.FundRepository;
import com.fundapp.repository.UserRepository;

@RestController
@RequestMapping("/fund")
public class FundController {

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundMemberRepository fundMemberRepository;

    @PostMapping("/create")
    public Fund createFund(@RequestBody CreateFundRequest request) {
        User admin = userRepository.findById(request.getAdminUserId())
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        Fund fund = new Fund();
        fund.setName(request.getFundName());
        fund.setMonthlyAmount(request.getMonthlyAmount());
        fund.setAdmin(admin);
        fund.setStartDate(LocalDate.now());
        fund.setTotalMembers(1);
        fund.setStatus("ACTIVE");

        Fund savedFund = fundRepository.save(fund);

        FundMember member = new FundMember();
        member.setFund(savedFund);
        member.setUser(admin);
        member.setRole("ADMIN");
        member.setJoinDate(LocalDate.now());

        fundMemberRepository.save(member);

        return savedFund;

    }

}
