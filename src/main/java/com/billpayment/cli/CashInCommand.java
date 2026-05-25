package com.billpayment.cli;

import com.billpayment.service.AccountService;

public class CashInCommand implements Command {
    private final AccountService accountService;

    public CashInCommand(AccountService accountService) {
        this.accountService = accountService;
    }
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid command");
            return;
        }

        try {
            long amount = Long.parseLong(args[1]);

            long balance = accountService.cashIn(amount);

            System.out.println("Your available balance: " + balance);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
