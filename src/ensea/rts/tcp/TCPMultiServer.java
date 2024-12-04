package ensea.rts.tcp;

import java.io.*;
import java.net.*;

public class TCPMultiServer {

    private int port;

    /**
     * Constructs a TCPMultiServer with the specified port.
     *
     * @param port the port number on which the server will listen for connections
     */
    public TCPMultiServer(int port) {
        this.port = port;
    }

    /**
     * Starts the TCP server. The server listens for incoming client connections
     * and creates a new ClientHandler thread for each connection.
     */
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    /**
     * The main method to start the TCP server.
     * It accepts an optional command line argument for the port number.
     */
    public static void main(String[] args) {
        int port = 9090;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port 9090.");
            }
        }
        TCPMultiServer server = new TCPMultiServer(port);
        server.startServer();
    }
}

/**
 * The ClientHandler class handles communication with a connected client.
 * It runs in a separate thread for each client connection.
 */
class ClientHandler extends Thread {
    private Socket clientSocket;
    private static final int maxBytes = 1024;
    private final byte[] buffer = new byte[maxBytes];

    /**
     * Constructs a ClientHandler with the specified client socket.
     *
     * @param socket the client socket
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    /**
     * Runs the client handler thread.
     * Handles communication with the connected client.
     */
    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             InputStream in = clientSocket.getInputStream()) {

            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, maxBytes)) != -1) {
                String message = new String(buffer, 0, bytesRead);
                InetAddress clientAddress = clientSocket.getInetAddress();
                int clientPort = clientSocket.getPort();
                System.out.println("Received from " + clientAddress + ":" + clientPort + " - " + message);
                out.println(clientAddress + ": " + message);
            }
        } catch (IOException e) {
            System.err.println("Client handler error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}