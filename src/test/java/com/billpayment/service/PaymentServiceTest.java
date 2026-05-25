package com.billpayment.service;

import com.billpayment.enumeric.BillState;
import com.billpayment.model.*;
import com.billpayment.storage.BillStorage;
import com.billpayment.storage.PaymentStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {
    private Account account;
    private BillStorage billStorage;
    private PaymentStorage paymentStorage;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        account = new Account();
        billStorage = new BillStorage();
        paymentStorage = new PaymentStorage();

        paymentService = new PaymentService(account, billStorage, paymentStorage);
    }

    @Test
    void shouldPaySingleBillSuccessfully() {

        account.addFund(1000);

        Bill bill = new Bill(1, "ELECTRIC", 500,
                LocalDate.of(2026, 10, 10), "EVN");

        billStorage.save(bill);

        long balance = paymentService.pay(1);

        assertEquals(500, balance);
        assertEquals(BillState.PAID, bill.getState());
        assertEquals(1, paymentStorage.findAll().size());
    }

    @Test
    void shouldPayMultipleBillsByDueDatePriority() {

        account.addFund(1000);

        Bill b1 = new Bill(1, "A", 400,
                LocalDate.of(2026, 10, 10), "X");

        Bill b2 = new Bill(2, "B", 300,
                LocalDate.of(2026, 10, 5), "Y");

        Bill b3 = new Bill(3, "C", 200,
                LocalDate.of(2026, 10, 1), "Z");

        billStorage.save(b1);
        billStorage.save(b2);
        billStorage.save(b3);

        paymentService.payMultiple(List.of(1L, 2L, 3L));

        // should pay in order: 3 -> 2 -> 1
        assertEquals(BillState.PAID, b3.getState());
        assertEquals(BillState.PAID, b2.getState());
        assertEquals(BillState.PAID, b1.getState());

        assertEquals(100, account.getBalance());
    }

    @Test
    void shouldStopWhenNotEnoughFund() {

        account.addFund(500);

        Bill b1 = new Bill(1, "A", 200,
                LocalDate.of(2026, 10, 10), "X");

        Bill b2 = new Bill(2, "B", 400,
                LocalDate.of(2026, 10, 5), "Y");

        Bill b3 = new Bill(3, "C", 100,
                LocalDate.of(2026, 10, 1), "Z");

        billStorage.save(b1);
        billStorage.save(b2);
        billStorage.save(b3);

        Exception ex = assertThrows(
                IllegalStateException.class,
                () -> paymentService.payMultiple(List.of(1L, 2L, 3L))
        );

        assertTrue(ex.getMessage().contains("Not enough money"));

        // b3 (earliest) should be paid first
        assertEquals(BillState.PAID, b3.getState());

        // others remain unpaid
        assertEquals(BillState.NOT_PAID, b1.getState());
    }

    @Test
    void shouldThrowWhenBillAlreadyPaid() {

        account.addFund(1000);

        Bill bill = new Bill(1, "A", 100,
                LocalDate.now(), "X");

        bill.markPaid();

        billStorage.save(bill);

        Exception ex = assertThrows(
                IllegalStateException.class,
                () -> paymentService.pay(1)
        );

        assertEquals("Bill already paid", ex.getMessage());
    }

    @Test
    void shouldThrowWhenNoBillsToPay() {

        account.addFund(1000);

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> paymentService.payMultiple(List.of(1L, 2L))
        );

        assertTrue(ex.getMessage().contains("Bill not found:"));
    }
}
