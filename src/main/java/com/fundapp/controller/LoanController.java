package com.fundapp.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundapp.dto.IssueLoanRequest;
import com.fundapp.entity.Fund;
import com.fundapp.entity.Loan;
import com.fundapp.entity.User;
import com.fundapp.repository.FundMemberRepository;
import com.fundapp.repository.FundRepository;
import com.fundapp.repository.LoanRepository;
import com.fundapp.repository.UserRepository;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundMemberRepository fundMemberRepository;

    @PostMapping("/issue")
    public String issueLoan(@RequestBody IssueLoanRequest request) {

        Fund fund = fundRepository.findById(request.getFundId())
                .orElseThrow(() -> new RuntimeException("Fund not found"));

        User user = userRepository.findByPhone(request.getPhone());
        if (user == null)
            return "User not found";

        boolean isMember = fundMemberRepository.existsByFund_IdAndUser_Id(fund.getId(), user.getId());

        if (!isMember)
            return "User is not a member of this fund";

        BigDecimal interestAmount = request.getLoanAmount().multiply(request.getInterestPercent())
                .divide(BigDecimal.valueOf(100));

        BigDecimal totalPayable = request.getLoanAmount().add(interestAmount);

        BigDecimal emi = totalPayable.divide(BigDecimal.valueOf(request.getMonths()), 2, BigDecimal.ROUND_HALF_UP);

        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy")).toUpperCase();
        Loan loan = new Loan();
        loan.setFund(fund);
        loan.setUser(user);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setInterestPercent(request.getInterestPercent());
        loan.setTotalPayable(totalPayable);
        loan.setMonthlyEmi(emi);
        loan.setRemainingAmount(totalPayable);
        loan.setStartMonth(month);
        loan.setMonths(request.getMonths());
        loan.setStatus("ACTIVE");
        loan.setCreatedAt(LocalDate.now());

        loanRepository.save(loan);

        return "Loan issued successfully";
    }
}
