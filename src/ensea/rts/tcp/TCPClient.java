package ensea.rts.tcp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPClient {

    /**
     * The main method to run the TCPClient.
     *
     * @param args Command line arguments: server address and port.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Missing arguments \"server address\" or \"port\".");
            return;
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server " + serverAddress + " on port " + port);

            while (scanner.hasNextLine()) {
                String userInput = scanner.nextLine();
                out.println(userInput);

                String serverResponse = in.readLine();
                System.out.println(serverResponse);
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}