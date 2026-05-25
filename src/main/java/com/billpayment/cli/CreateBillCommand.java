package com.billpayment.cli;

import com.billpayment.model.Bill;
import com.billpayment.service.BillService;

import java.time.LocalDate;

public class CreateBillCommand implements Command {

    private final BillService billService;

    public CreateBillCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        try {
            long id = Long.parseLong(args[1]);
            String type = args[2];
            long amount = Long.parseLong(args[3]);
            LocalDate dueDate = LocalDate.parse(args[4]);
            String provider = args[5];
            Bill bill = new Bill(id, type, amount, dueDate, provider);
            billService.create(bill);
            System.out.println("Bill created successfully");
        } catch (Exception e) {
            System.out.println("Invalid CREATE_BILL command: " + e.getMessage());
        }
    }
}