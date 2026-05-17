package com.fundapp.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class IssueLoanRequest {

    @NotNull(message = "Fund ID is required")
    private Long fundId;

    @NotBlank(message = "Phone number is required")
    private String phone;

    
    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be positive")
    private BigDecimal loanAmount;
    
    @NotNull(message = "Interest percent is required")
    @Positive(message = "Interest percent must be positive")
    private BigDecimal interestPercent;
    
    @Positive(message = "Months must be greater than 0")
    private int months;
    
    // getters and setters
    
    public Long getFundId() {
        return fundId;
    }
    
    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public BigDecimal getLoanAmount() {
        return loanAmount;
    }
    
    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }
    
    public BigDecimal getInterestPercent() {
        return interestPercent;
    }
    
    public void setInterestPercent(BigDecimal interestPercent) {
        this.interestPercent = interestPercent;
    }
    
    public int getMonths() {
        return months;
    }
    
    public void setMonths(int months) {
        this.months = months;
    }
}