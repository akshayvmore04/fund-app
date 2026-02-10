package com.fundapp.dto;

import java.math.BigDecimal;

public class FundDashboardResponse {

    private int totalMembers;
    private BigDecimal monthlyAmount;
    private BigDecimal totalExpected;
    private BigDecimal totalCollected;
    private BigDecimal totalPending;

    public FundDashboardResponse(int totalMembers,
            BigDecimal monthlyAmount,
            BigDecimal totalExpected,
            BigDecimal totalCollected,
            BigDecimal totalPending) {
        this.totalMembers = totalMembers;
        this.monthlyAmount = monthlyAmount;
        this.totalExpected = totalExpected;
        this.totalCollected = totalCollected;
        this.totalPending = totalPending;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public BigDecimal getMonthlyAmount() {
        return monthlyAmount;
    }

    public BigDecimal getTotalExpected() {
        return totalExpected;
    }

    public BigDecimal getTotalCollected() {
        return totalCollected;
    }

    public BigDecimal getTotalPending() {
        return totalPending;
    }

}
