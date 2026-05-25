package com.billpayment.model;

public class Account {
    private long balance = 0;

    public void addFund(long amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        this.balance += amount;
    }

    public long getBalance() {
        return balance;
    }
}
