package telran.net;

import telran.view.*;

public class Main {
    static Client client;
    static String choice = "";

    public static void main(String[] args) {
        Item[] items = {
                Item.of("start session", Main::startSession),
                Item.of("exit", Main::exit, true)
        };
        Menu menu = new Menu("Client-Server Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Wrong port", 3000, 50000).intValue();
        if (client != null) {
            client.close();
        }
        client = new Client(host, port);

        Menu menu = new Menu("Chose request",
                Item.of("Return reverse string", Main::reverseProcessing),
                Item.of("Return length of string", Main::lengthProcessing),
                Item.ofGoBack());
        menu.perform(io);

        Menu subMenu = new Menu("Run session",
                Item.of("Enter string", Main::stringProcessing),
                Item.ofExit());
        subMenu.perform(io);
    }

    static void reverseProcessing(InputOutput io) {
        choice = "reverse";
    }

    static void lengthProcessing(InputOutput io) {
        choice = "length";
    }

    static void stringProcessing(InputOutput io) {
        String string = io.readString("Enter any string");
        String response = client.sendAndReceive(string, choice);
        io.writeLine(response);
    }

    static void exit(InputOutput io) {
        if (client != null) {
            client.close();
        }
    }
}