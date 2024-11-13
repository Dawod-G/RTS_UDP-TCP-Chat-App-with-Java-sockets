package ensea.rts.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public int port;

    /**
     * The maximum number of bytes that can be read from the client at a time.
     */
    private static final int maxBytes = 1024;

    /**
     * The buffer used to store data received from the client.
     */
    private final byte[] buffer = new byte[maxBytes];

    /**
     * Constructs a TCPServer with the specified port number.
     *
     * @param port the port number on which the server listens for client connections
     */
    public TCPServer(int port) {
        this.port = port;
    }

    public TCPServer() {
        this.port = 9090;
    }

    public void launch() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);

            while (true) {
                // Accept a client connection and get its output screen
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected from " + clientSocket.getInetAddress());
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Receive data from the client
                    InputStream received = clientSocket.getInputStream();
                    int bytesRead;

                    // Display the received message in the console
                    while ((bytesRead = received.read(buffer, 0, maxBytes)) != -1) {
                        String message = new String(buffer, 0, bytesRead);
                        InetAddress clientAddress = clientSocket.getInetAddress();
                        int clientPort = clientSocket.getPort();
                        System.out.println("Received from " + clientAddress + ":" + clientPort + " - " + message);
                        output.println("Echo from server: " + message);
                    }

                    // Display the disconnection of the client
                    System.out.println("Client disconnected.");
                } catch (IOException e) {
                    System.err.println("Client connection error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    /**
     * The main method to start the TCP server.
     *
     * @param args command-line arguments (if provided, the first argument is used as the port number)
     */
    public static void main(String[] args) {
        int port = 9090;
        if (args.length > 0) {
            TCPServer server = new TCPServer(port);
            server.launch();
        } else {
            TCPServer server = new TCPServer();
            server.launch();
        }
    }
}