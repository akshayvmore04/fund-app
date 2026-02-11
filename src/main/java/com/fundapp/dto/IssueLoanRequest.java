package com.fundapp.dto;

import java.math.BigDecimal;

public class IssueLoanRequest {

    private Long fundId;
    private String phone;
    private BigDecimal loanAmount;
    private BigDecimal interestPercent;
    private int months;

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
