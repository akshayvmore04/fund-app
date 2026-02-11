package com.fundapp.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundapp.dto.EmiRequestDto;
import com.fundapp.dto.IssueLoanRequest;
import com.fundapp.entity.Fund;
import com.fundapp.entity.Loan;
import com.fundapp.entity.LoanEmi;
import com.fundapp.entity.User;
import com.fundapp.repository.FundMemberRepository;
import com.fundapp.repository.FundRepository;
import com.fundapp.repository.LoanEmiRepository;
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

    @Autowired
    private LoanEmiRepository loanEmiRepository;

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

    @PostMapping("/emi/request")
    public String requestEmi(@RequestBody EmiRequestDto request) {
        Loan loan = loanRepository.findById(request.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // System.out.println("Loan EMI = " + loan.getMonthlyEmi());

        if (!loan.getStatus().equals("ACTIVE")) {
            return "Loan is not active";

        }
        boolean exists = loanEmiRepository.existsByLoanIdAndMonth(loan.getId(), request.getMonth());
        if (exists) {
            return "EMI already requested for this month";
        }
        LoanEmi emi = new LoanEmi();
        emi.setLoan(loan);
        emi.setMonth(request.getMonth());
        emi.setAmount(loan.getMonthlyEmi());
        emi.setStatus("PENDING");
        emi.setRequestDate(java.time.LocalDate.now());

        loanEmiRepository.save(emi);

        return "EMI request submitted";
    }

    @PostMapping("/emi/approve/{emiId}")
    public String approveEmi(@PathVariable Long emiId) {

        LoanEmi emi = loanEmiRepository.findById(emiId).orElseThrow(() -> new RuntimeException("EMI not found"));
        if ("PAID".equals(emi.getStatus())) {
            return "EMI already approved";
        }
        if (emi.getAmount() == null) {
            return "EMI amount missing";
        }
        // makrkin emi paid
        emi.setStatus("PAID");
        emi.setApprovedDate(java.time.LocalDate.now());
        loanEmiRepository.save(emi);

        // reduce remaining amount
        Loan loan = emi.getLoan();
        java.math.BigDecimal newRemaining = loan.getRemainingAmount().subtract(emi.getAmount());

        loan.setRemainingAmount(newRemaining);

        // close if finished
        if (newRemaining.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            loan.setStatus("CLOSED");
        }

        loanRepository.save(loan);

        return "EMI approved successfully";
    }
}
