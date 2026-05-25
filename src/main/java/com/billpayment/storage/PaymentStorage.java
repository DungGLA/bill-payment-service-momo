package com.billpayment.storage;

import com.billpayment.model.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentStorage {
    private final List<Payment> payments = new ArrayList<>();

    public void save(Payment payment) {
        payments.add(payment);
    }

    public List<Payment> findAll() {
        return payments;
    }
}
