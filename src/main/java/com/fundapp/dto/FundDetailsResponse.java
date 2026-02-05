package com.fundapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FundDetailsResponse {

    private Long fundId;
    private String fundName;
    private BigDecimal monthlyAmount;
    private String status;
    private LocalDate startDate;
    private int totalMembers;
    private String adminName;
    private String adminPhone;
    private List<MemberResponse> members;

    public FundDetailsResponse(
            Long fundId,
            String fundName,
            BigDecimal monthlyAmount,
            String status,
            LocalDate startDate,
            int totalMembers,
            String adminName,
            String adminPhone,
            List<MemberResponse> members) {
        this.fundId = fundId;
        this.fundName = fundName;
        this.monthlyAmount = monthlyAmount;
        this.status = status;
        this.startDate = startDate;
        this.totalMembers = totalMembers;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.members = members;
    }

    public Long getFundId() {
        return fundId;
    }

    public String getFundName() {
        return fundName;
    }

    public BigDecimal getMonthlyAmount() {
        return monthlyAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public List<MemberResponse> getMembers() {
        return members;
    }
}
