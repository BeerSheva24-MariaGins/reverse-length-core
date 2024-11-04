package telran.net;

import java.io.*;
import java.net.*;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();

            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream writer = new PrintStream(socket.getOutputStream())) {
            String line = "";

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                String choice = parts[0];
                String inputString = parts.length > 1 ? parts[1] : "";

                if (choice.equals("length")) {
                    writer.printf("Echo server on %s, port: %d sends back %s\n",
                            socket.getLocalAddress().getHostAddress(), socket.getLocalPort(), inputString.length());
                } else if (choice.equals("reverse")) {
                    writer.printf("Echo server on %s, port: %d sends back :: %s\n",
                            socket.getLocalAddress().getHostAddress(), socket.getLocalPort(),
                            new StringBuilder(inputString).reverse().toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Client closed connection abnormally");
        }
    }
}