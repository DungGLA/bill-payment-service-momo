package com.billpayment.cli;

import java.util.HashMap;
import java.util.Map;
public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    public void register(String name, Command command) {
        commands.put(name, command);
    }

    public Command get(String name) {
        return commands.get(name);
    }
}
