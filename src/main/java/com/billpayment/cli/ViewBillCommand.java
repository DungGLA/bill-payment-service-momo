package com.billpayment.cli;

import com.billpayment.service.BillService;

public class ViewBillCommand implements Command {

    private final BillService billService;

    public ViewBillCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        billService.getAll().forEach(bill -> {
            System.out.println(
                    bill.getId() + " " +
                            bill.getType() + " " +
                            bill.getAmount() + " " +
                            bill.getDueDate() + " " +
                            bill.getState() + " " +
                            bill.getProvider()
            );
        });
    }
}
