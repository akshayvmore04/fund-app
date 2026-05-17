package com.fundapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentHistoryDto {
    
    private String month;
    private BigDecimal amount;
    private String status;
    private LocalDate paymentDate;

     public PaymentHistoryDto(
            String month,
            BigDecimal amount,
            String status,
            LocalDate paymentDate
     ) {
         this.month = month;
         this.amount = amount;
         this.status = status;
         this.paymentDate = paymentDate;
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
