package com.fundapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundapp.dto.ApiResponse;
import com.fundapp.entity.Loan;
import com.fundapp.entity.LoanEmi;
import com.fundapp.exception.ResourceNotFoundException;
import com.fundapp.repository.LoanEmiRepository;
import com.fundapp.repository.LoanRepository;

@Service
public class LoanService {

    @Autowired
    private LoanEmiRepository loanEmiRepository;

    @Autowired
    private LoanRepository loanRepository;

    public ApiResponse<String> approveEmi(Long emiId) {
        LoanEmi emi = loanEmiRepository.findById(emiId).orElseThrow(() -> new ResourceNotFoundException("EMI not found"));
        if ("PAID".equals(emi.getStatus())) {
return new ApiResponse<>(
        false,
        "EMI already approved",
        null
);        }
        if (emi.getAmount() == null) {
return new ApiResponse<>(
        false,
        "EMI amount missing",
        null
);        }
        // get loan
        Loan loan = emi.getLoan();

        // makrkin emi paid
        emi.setStatus("PAID");
        emi.setApprovedDate(java.time.LocalDate.now());
        loanEmiRepository.save(emi);

        // reduce remaining amount
        java.math.BigDecimal newRemaining = loan.getRemainingAmount().subtract(emi.getAmount());

        loan.setRemainingAmount(newRemaining);

        // close if finished
        if (newRemaining.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            loan.setStatus("CLOSED");
        }

        loanRepository.save(loan);

return new ApiResponse<>(
        true,
        "EMI approved successfully",
        null
);    }
}
