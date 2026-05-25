package com.billpayment.cli;

import com.billpayment.service.BillService;

public class DeleteBillCommand implements Command {

    private final BillService billService;

    public DeleteBillCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        try {
            long id = Long.parseLong(args[1]);
            billService.delete(id);
            System.out.println("Bill deleted successfully");
        } catch (Exception e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }
}