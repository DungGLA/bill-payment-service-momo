package com.billpayment.cli;

import com.billpayment.model.Bill;
import com.billpayment.service.BillService;

import java.util.List;
public class DueDateCommand implements Command {

    private final BillService billService;

    public DueDateCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {

        List<Bill> bills = billService.getBillsSortedByDueDate();

        System.out.println("Bill No. Type Amount Due Date State Provider");

        for (Bill b : bills) {

            System.out.println(
                    b.getId() + " " +
                            b.getType() + " " +
                            b.getAmount() + " " +
                            b.getDueDate() + " " +
                            b.getState() + " " +
                            b.getProvider()
            );
        }
    }
}