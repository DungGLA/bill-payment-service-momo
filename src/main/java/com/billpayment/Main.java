package com.billpayment;

import com.billpayment.cli.*;
import com.billpayment.model.Account;
import com.billpayment.service.AccountService;
import com.billpayment.service.BillService;
import com.billpayment.service.PaymentService;
import com.billpayment.storage.BillStorage;
import com.billpayment.storage.PaymentStorage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        AccountService accountService = new AccountService(account);

        BillStorage billStorage = new BillStorage();
        BillService billService = new BillService(billStorage);

        PaymentStorage paymentStorage = new PaymentStorage();
        PaymentService paymentService = new PaymentService(account, billStorage, paymentStorage);

        CommandRegistry registry = new CommandRegistry();

        registry.register("CASH_IN", new CashInCommand(accountService));
        // BILL COMMANDS
        registry.register("CREATE_BILL", new CreateBillCommand(billService));

        registry.register("LIST_BILL", new ViewBillCommand(billService));

        registry.register("UPDATE_BILL", new UpdateBillCommand(billService));

        registry.register("DELETE_BILL", new DeleteBillCommand(billService));

        registry.register("SEARCH_BILL_BY_PROVIDER", new SearchBillCommand(billService));

        // PAYMENT
        registry.register("PAY", new PayCommand(paymentService));
        registry.register("LIST_PAYMENT", new ListPaymentCommand(paymentService));
        registry.register("SCHEDULE", new ScheduleCommand(billService, paymentStorage));

        registry.register("DUE_DATE", new DueDateCommand(billService));

        System.out.println("Bill payment service start!");
        Scanner scanner = new Scanner(System.in);
        while (true) {

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Good bye!");
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+");

            String commandName = parts[0];

            Command command = registry.get(commandName);

            if (command == null) {
                System.out.println("Unknown command");
                continue;
            }

            command.execute(parts);
        }
    }
}