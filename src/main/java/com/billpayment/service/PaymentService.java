package com.billpayment.service;

import com.billpayment.enumeric.BillState;
import com.billpayment.enumeric.PaymentStatus;
import com.billpayment.model.*;
import com.billpayment.storage.BillStorage;
import com.billpayment.storage.PaymentStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PaymentService {
    private final Account account;
    private final BillStorage billStorage;
    private final PaymentStorage paymentStorage;

    private long paymentId = 1;

    public PaymentService(Account account,
                          BillStorage billStorage,
                          PaymentStorage paymentStorage) {
        this.account = account;
        this.billStorage = billStorage;
        this.paymentStorage = paymentStorage;
    }

    public long pay(long billId) {

        Bill bill = billStorage.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Sorry! Not found a bill with such id"));

        if (bill.getState() == BillState.PAID) {
            throw new IllegalStateException("Bill already paid");
        }

        if (account.getBalance() < bill.getAmount()) {
            throw new IllegalStateException("Sorry! Not enough fund to proceed with payment.");
        }

        account.deduct(bill.getAmount());
        bill.markPaid();

        Payment payment = new Payment(
                paymentId++,
                bill.getId(),
                bill.getAmount(),
                LocalDate.now(),
                PaymentStatus.PROCESSED
        );

        paymentStorage.save(payment);
        return account.getBalance();
    }

    public void payMultiple(List<Long> billIds) {

        List<Bill> billsToPay = billIds.stream()
                .map(id -> billStorage.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Bill not found: " + id)))
                .filter(bill -> bill.getState() == BillState.NOT_PAID)
                .sorted(Comparator.comparing(Bill::getDueDate))
                .toList();

        if (billsToPay.isEmpty()) {
            throw new IllegalStateException("No bills to pay.");
        }

        Set<Long> payedBillIds = new TreeSet<>();
        for (Bill bill : billsToPay) {
            // stop when not enough fund
            if (account.getBalance() < bill.getAmount()) {
                throw new IllegalStateException("Not enough money to pay the bill id: " + bill.getId() + ". Payment would be prioritized for bill with early due dates.");
            }

            account.deduct(bill.getAmount());
            bill.markPaid();

            Payment payment = new Payment(
                    paymentId++,
                    bill.getId(),
                    bill.getAmount(),
                    LocalDate.now(),
                    PaymentStatus.PROCESSED
            );

            paymentStorage.save(payment);
            payedBillIds.add(bill.getId());
        }

        if (!payedBillIds.isEmpty()) {
            System.out.println("Bills paid: " + payedBillIds);
            System.out.println("Payment completed. Current balance: " + account.getBalance());
        }
    }
}
