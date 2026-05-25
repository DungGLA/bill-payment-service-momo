package com.billpayment.cli;

import com.billpayment.service.BillService;

public class SearchBillCommand implements Command {

    private final BillService billService;

    public SearchBillCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        String provider = args[1];
        billService.searchByProvider(provider)
                .forEach(bill -> System.out.println(
                        bill.getId() + " " +
                                bill.getType() + " " +
                                bill.getAmount() + " " +
                                bill.getDueDate() + " " +
                                bill.getState() + " " +
                                bill.getProvider()
                ));
    }
}