package com.billpayment.model;

import com.billpayment.enumeric.PaymentStatus;

import java.time.LocalDate;

public class Payment {
    private long id;
    private long billId;
    private long amount;
    private LocalDate paymentDate;
    private PaymentStatus status;

    public Payment(long id, long billId, long amount,
                   LocalDate paymentDate, PaymentStatus status) {
        this.id = id;
        this.billId = billId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public long getBillId() {
        return billId;
    }
}
