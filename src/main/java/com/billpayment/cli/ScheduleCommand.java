package com.billpayment.cli;

import com.billpayment.enumeric.BillState;
import com.billpayment.enumeric.PaymentStatus;
import com.billpayment.model.Bill;
import com.billpayment.model.Payment;
import com.billpayment.service.BillService;
import com.billpayment.storage.PaymentStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleCommand implements Command {

    private final BillService billService;
    private final PaymentStorage paymentStorage;

    private long paymentId = 1;

    public ScheduleCommand(BillService billService,
                           PaymentStorage paymentStorage) {
        this.billService = billService;
        this.paymentStorage = paymentStorage;
    }

    @Override
    public void execute(String[] args) {

        try {
            if (args.length < 3) {
                System.out.println("Invalid command. Usage: SCHEDULE <billId> <dd/MM/yyyy>");
                return;
            }

            long billId = Long.parseLong(args[1]);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate paymentDate;
            try {
                paymentDate = LocalDate.parse(args[2], formatter);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Wrong format date");
                return;
            }

            // 1. check bill exists
            Bill bill = billService.findById(billId);

            // 2. check already paid
            if (bill.getState() == BillState.PAID) {
                throw new IllegalStateException("Bill already paid");
            }

            // 3. create PENDING payment
            Payment payment = new Payment(
                    paymentId++,
                    billId,
                    bill.getAmount(),
                    paymentDate,
                    PaymentStatus.PENDING
            );

            paymentStorage.save(payment);

            System.out.println(
                    "Payment for bill id " + billId +
                            " is scheduled on " + paymentDate
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}