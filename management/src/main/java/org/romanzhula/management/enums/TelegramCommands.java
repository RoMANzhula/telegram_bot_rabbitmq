package org.romanzhula.management.enums;

public enum TelegramCommands {

    START("/start"),
    HELP("/help"),
    REGISTRATION("/registration"),
    CANCEL("/cancel")
    ;

    private final String command;

    TelegramCommands(String command) {
        this.command = command;
    }


    public static TelegramCommands fromCommand(String command) {
        for (TelegramCommands element : TelegramCommands.values()) {
            if (element.command.equals(command)) {
                return element;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return  command;
    }

}
