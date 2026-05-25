package com.billpayment.cli;

import com.billpayment.service.BillService;
import java.time.LocalDate;

public class UpdateBillCommand implements Command {

    private final BillService billService;

    public UpdateBillCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void execute(String[] args) {
        try {
            long id = Long.parseLong(args[1]);
            long amount = Long.parseLong(args[2]);
            String provider = args[3];
            LocalDate dueDate = LocalDate.parse(args[4]);
            billService.update(id, amount, provider, dueDate);
            System.out.println("Bill updated successfully");
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
}
