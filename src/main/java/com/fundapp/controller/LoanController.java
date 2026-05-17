package com.fundapp.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundapp.dto.ApiResponse;
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
import com.fundapp.service.LoanService;

import jakarta.validation.Valid;

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

    @Autowired
    private LoanService loanService;

    @PostMapping("/issue")
    public ApiResponse<String> issueLoan(@Valid @RequestBody IssueLoanRequest request) {

        Fund fund = fundRepository.findById(request.getFundId())
                .orElseThrow(() -> new RuntimeException("Fund not found"));

        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isMember = fundMemberRepository.existsByFund_IdAndUser_Id(fund.getId(), user.getId());

        if (!isMember)
return new ApiResponse<>(
        false,
        "User is not a member of this fund",
        null
);
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

        return new ApiResponse<>(
        true,
        "Loan issued successfully",
        null
);
    }

    @PostMapping("/emi/request")
    public ApiResponse<String> requestEmi(@Valid @RequestBody EmiRequestDto request) {
        Loan loan = loanRepository.findById(request.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String phone = auth.getName();

        User user = userRepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("User not found"));
        if (!loan.getUser().getId().equals(user.getId())) {
return new ApiResponse<>(
        false,
        "You cannot request EMI for another user's loan",
        null
);        }
        // System.out.println("Loan EMI = " + loan.getMonthlyEmi());

        if (!loan.getStatus().equals("ACTIVE")) {
return new ApiResponse<>(
        false,
        "Loan is not active",
        null
);
        }
        boolean exists = loanEmiRepository.existsByLoanIdAndMonth(loan.getId(), request.getMonth());
        if (exists) {
return new ApiResponse<>(
        false,
        "EMI already requested for this month",
        null
);        }
        LoanEmi emi = new LoanEmi();
        emi.setLoan(loan);
        emi.setMonth(request.getMonth());
        emi.setAmount(loan.getMonthlyEmi());
        emi.setStatus("PENDING");
        emi.setRequestDate(java.time.LocalDate.now());

        loanEmiRepository.save(emi);

return new ApiResponse<>(
        true,
        "EMI request submitted",
        null
);    }

    @PostMapping("/emi/approve/{emiId}")
    public ApiResponse<String> approveEmi(@PathVariable("emiId") Long emiId){
        return loanService.approveEmi(emiId);
    }
}
