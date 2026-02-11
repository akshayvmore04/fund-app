package com.fundapp.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_emis")
public class LoanEmi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Loan loan;

    private String month;

    private BigDecimal amount;

    private String status; // PENDING, PAID

    private LocalDate requestDate;

    private LocalDate approvedDate;

    public LoanEmi() {}

    // getters & setters
    public Long getId() { return id; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }

    public LocalDate getApprovedDate() { return approvedDate; }
    public void setApprovedDate(LocalDate approvedDate) { this.approvedDate = approvedDate; }
}
