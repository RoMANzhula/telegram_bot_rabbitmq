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


    public boolean equals(String command) {
        return this.toString().equals(command);
    }

    @Override
    public String toString() {
        return  command;
    }

}
