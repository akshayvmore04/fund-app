package com.fundapp.dto;

import java.math.BigDecimal;

public class PendingPaymentResponse {

    private Long paymentId;
    private String memberName;
    private String phone;
    private String month;
    private BigDecimal amount;

    public PendingPaymentResponse(Long paymentId, String memberName, String phone, String month, BigDecimal amount) {
        this.paymentId = paymentId;
        this.memberName = memberName;
        this.phone = phone;
        this.month = month;
        this.amount = amount;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
