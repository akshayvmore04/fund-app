package com.fundapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentHistoryResponse {
    private String memberName;
    private String phone;
    private String month;
    private BigDecimal amount;
    private String status;
    private LocalDate paymentDate;

    public PaymentHistoryResponse(String memberName, String phone,
            String month, BigDecimal amount,
            String status, LocalDate paymentDate) {
        this.memberName = memberName;
        this.phone = phone;
        this.month = month;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getPhone() {
        return phone;
    }

    public String getMonth() {
        return month;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

}
