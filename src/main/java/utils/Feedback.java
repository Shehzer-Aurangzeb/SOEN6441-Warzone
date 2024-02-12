package main.java.utils;

import main.java.models.Commands.Commands;

public class Feedback {
    public static void displayWelcomeMessage() {
        System.out.println("Welcome to the Risk Game!");
        System.out.println("Before we begin, here are a few commands you can use\n");
        System.out.println("\t-loadmap <filename>: Load a map file to start the game.\n" +
                "\t-showcommands: Display all available commands.\n" +
                "\t-exit: Exit the game.\n" +
                "\nLet's embark on your conquest! Use 'loadmap <filename>' to begin.");
    }

    public static void displayAllCommands() {
        System.out.println(Commands.getAllCommands());
    }

}
