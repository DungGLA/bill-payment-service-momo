package com.billpayment;

import com.billpayment.cli.CashInCommand;
import com.billpayment.cli.Command;
import com.billpayment.cli.CommandRegistry;
import com.billpayment.model.Account;
import com.billpayment.service.AccountService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        AccountService accountService = new AccountService(account);

        CommandRegistry registry = new CommandRegistry();

        registry.register("CASH_IN", new CashInCommand(accountService));

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