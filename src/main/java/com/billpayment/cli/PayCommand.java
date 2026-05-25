package com.billpayment.cli;

import com.billpayment.service.PaymentService;

import java.util.ArrayList;
import java.util.List;

public class PayCommand implements Command {
    private final PaymentService paymentService;

    public PayCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void execute(String[] args) {

        try {
            List<Long> billIds = new ArrayList<>();

            if (args.length == 1) {
                paySingleBill(args);
                return;
            }

            for (int i = 1; i < args.length; i++) {
                billIds.add(Long.parseLong(args[i]));
            }

            paymentService.payMultiple(billIds);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void paySingleBill(String[] args) {
        try {
            long billId = Long.parseLong(args[1]);

            long balance = paymentService.pay(billId);

            System.out.println("Payment has been completed for Bill with id " + billId);
            System.out.println("Your current balance is: " + balance);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
