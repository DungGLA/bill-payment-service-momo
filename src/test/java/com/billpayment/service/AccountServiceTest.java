package com.billpayment.service;

import com.billpayment.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AccountServiceTest {

    private Account account;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        account = new Account();
        accountService = new AccountService(account);
    }

    @Test
    void shouldCashInSuccessfully() {

        long balance = accountService.cashIn(1000);

        assertEquals(1000, balance);
        assertEquals(1000, account.getBalance());
    }

    @Test
    void shouldAccumulateBalanceWhenCashInMultipleTimes() {

        accountService.cashIn(1000);
        accountService.cashIn(500);

        assertEquals(1500, account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.cashIn(0)
        );

        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.cashIn(-100)
        );

        assertEquals("Amount must be positive", exception.getMessage());
    }
}
