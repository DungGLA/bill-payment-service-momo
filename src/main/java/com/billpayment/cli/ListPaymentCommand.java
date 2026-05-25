package com.billpayment.cli;

import com.billpayment.model.Payment;
import com.billpayment.service.PaymentService;

import java.util.List;
public class ListPaymentCommand implements Command {

    private final PaymentService paymentService;

    public ListPaymentCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void execute(String[] args) {

        List<Payment> payments = paymentService.getPaymentHistory();

        System.out.println("No. Amount Payment Date State Bill Id");

        int index = 1;

        for (Payment payment : payments) {

            System.out.println(
                    index++ + ". " +
                            payment.getAmount() + " " +
                            payment.getPaymentDate() + " " +
                            payment.getStatus() + " " +
                            payment.getBillId()
            );
        }
    }
}