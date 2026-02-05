package com.fundapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundapp.dto.AddMemberRequest;
import com.fundapp.dto.CreateFundRequest;
import com.fundapp.dto.FundDetailsResponse;
import com.fundapp.dto.MemberResponse;
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

    @PostMapping("/add-member")
    public String addMember(@RequestBody AddMemberRequest request) {
        Fund fund = fundRepository.findById(request.getFundId())
                .orElseThrow(() -> new RuntimeException("Fund not found"));

        User user = userRepository.findByPhone(request.getPhone());
        if (user == null) {
            return "User not registered";
        }
        boolean alreadyMember = fundMemberRepository.existsByFund_IdAndUser_Id(fund.getId(), user.getId());

        if (alreadyMember) {
            return "User already a member of this fund";
        }

        FundMember member = new FundMember();
        member.setFund(fund);
        member.setUser(user);
        member.setRole("MEMBER");
        member.setJoinDate(LocalDate.now());

        fundMemberRepository.save(member);

        fund.setTotalMembers(fund.getTotalMembers() + 1);
        fundRepository.save(fund);

        return "Member added succeessfully";

    }

    @GetMapping("/{fundId}")
    public FundDetailsResponse getFundDetails(@PathVariable Long fundId) {
        Fund fund = fundRepository.findById(fundId).orElseThrow(() -> new RuntimeException("Fund not found"));

        List<FundMember> fundMembers = fundMemberRepository.findByFund_Id(fundId);

        List<MemberResponse> members = fundMembers.stream().map(
                m -> new MemberResponse(m.getUser().getName(), m.getUser().getPhone(), m.getRole(), m.getJoinDate()))
                .toList();

        return new FundDetailsResponse(fund.getId(),
                fund.getName(),
                fund.getMonthlyAmount(),
                fund.getStatus(),
                fund.getStartDate(),
                fund.getTotalMembers(),
                fund.getAdmin().getName(),
                fund.getAdmin().getPhone(),
                members);
    }
}
