package com.fundapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundapp.dto.ApprovePaymentRequest;
import com.fundapp.dto.DefaulterResponse;
import com.fundapp.dto.PaymentHistoryResponse;
import com.fundapp.dto.PaymentRequest;
import com.fundapp.dto.PendingPaymentResponse;
import com.fundapp.entity.Fund;
import com.fundapp.entity.Payment;
import com.fundapp.entity.User;
import com.fundapp.repository.FundMemberRepository;
import com.fundapp.repository.FundRepository;
import com.fundapp.repository.PaymentRepository;
import com.fundapp.repository.UserRepository;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundMemberRepository fundMemberRepository;

    @PostMapping("/request")
    public String requestPayment(@RequestBody PaymentRequest request) {

        Fund fund = fundRepository.findById(request.getFundId())
                .orElseThrow(() -> new RuntimeException("Fund not found"));

        User user = userRepository.findByPhone(request.getPhone());
        if (user == null)
            return "User not found";

        boolean isMember = fundMemberRepository
                .existsByFund_IdAndUser_Id(fund.getId(), user.getId());

        if (!isMember)
            return "User is not a member of this fund";

        boolean alreadyExists = paymentRepository
                .existsByFundIdAndUserIdAndMonth(
                        fund.getId(),
                        user.getId(),
                        request.getMonth());

        if (alreadyExists)
            return "Payment request already exists";

        Payment payment = new Payment();
        payment.setFund(fund);
        payment.setUser(user);
        payment.setMonth(request.getMonth());
        payment.setAmount(fund.getMonthlyAmount());
        payment.setStatus("PENDING");

        paymentRepository.save(payment);

        return "Payment request submitted for approval";
    }

    @PostMapping("/approve")
    public String approvePayment(@RequestBody ApprovePaymentRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        if (!payment.getStatus().equals("PENDING")) {
            return "Payment aready processed";
        }

        payment.setStatus("PAID");
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);

        return "Payment approved successfully";
    }

    @GetMapping("/pending/{fundId}")
    public List<PendingPaymentResponse> getPendingPayments(@PathVariable Long fundId) {
        List<Payment> payments = paymentRepository.findByFundIdAndStatus(fundId, "PENDING");
        return payments.stream().map(p -> new PendingPaymentResponse(
                p.getId(),
                p.getUser().getName(),
                p.getUser().getPhone(),
                p.getMonth(),
                p.getAmount())).toList();
    }

    @GetMapping("/defaulters/{fundId}/{month}")
    public List<DefaulterResponse> getDefaulters(@PathVariable Long fundId, @PathVariable String month) {
        // all users
        var members = fundMemberRepository.findByFund_Id(fundId);
        // users done with payment
        var paidPayments = paymentRepository.findByFundIdAndMonthAndStatus(fundId, month, "PAID");

        // extract users done with payment
        var paidUserIds = paidPayments.stream().map(p -> p.getUser().getId()).toList();

        // filter defaulters
        return members.stream().filter(m -> !paidUserIds.contains(m.getUser().getId()))
                .map(m -> new DefaulterResponse(m.getUser().getName(), m.getUser().getPhone())).toList();

    }

    @GetMapping("/history/{fundId}")
    public List<PaymentHistoryResponse> getPaymentHistory(@PathVariable Long fundId) {
        List<Payment> payments = paymentRepository.findByFundId(fundId);

        return payments.stream().map(p -> new PaymentHistoryResponse(p.getUser().getName(), p.getUser().getPhone(),
                p.getMonth(), p.getAmount(), p.getStatus(), p.getPaymentDate())).toList();
    }

    // OLD FLOW - direct pay (not used anymore)
    /*
     * @PostMapping("/pay")
     * public String pay(@RequestBody PaymentRequest request) {
     * 
     * // Find fund
     * Fund fund = fundRepository.findById(request.getFundId())
     * .orElseThrow(() -> new RuntimeException("Fund not found"));
     * 
     * // Find user
     * User user = userRepository.findByPhone(request.getPhone());
     * if (user == null) {
     * return "User not found";
     * }
     * 
     * // Check membership
     * boolean isMember =
     * fundMemberRepository.existsByFund_IdAndUser_Id(fund.getId(), user.getId());
     * 
     * if (!isMember) {
     * return "User is not a member of this fund";
     * }
     * 
     * // Prevent duplicate
     * boolean alreadyPaid = paymentRepository
     * .existsByFundIdAndUserIdAndMonth(
     * fund.getId(),
     * user.getId(),
     * request.getMonth()
     * );
     * 
     * if (alreadyPaid) {
     * return "Payment already done for this month";
     * }
     * 
     * // Save payment
     * Payment payment = new Payment();
     * payment.setFund(fund);
     * payment.setUser(user);
     * payment.setMonth(request.getMonth());
     * payment.setAmount(fund.getMonthlyAmount());
     * payment.setStatus("PAID");
     * payment.setPaymentDate(LocalDate.now());
     * 
     * paymentRepository.save(payment);
     * 
     * return "Payment successful";
     * }
     */
}
