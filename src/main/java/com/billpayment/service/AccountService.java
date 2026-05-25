package com.billpayment.service;

import com.billpayment.model.Account;

public class AccountService {
    private final Account account;

    public AccountService(Account account) {
        this.account = account;
    }

    public long cashIn(long amount) {
        account.addFund(amount);
        return account.getBalance();
    }
}
