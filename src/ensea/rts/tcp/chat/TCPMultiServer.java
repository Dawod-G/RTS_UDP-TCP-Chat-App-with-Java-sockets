package ensea.rts.tcp.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TCPMultiServer {

    private final int port;
    private static final Map<String, ClientHandler> clients = new HashMap<>();

    public TCPMultiServer(int port) {
        if (port < 0 || port > 49151) {
            System.err.println("Port number must be between 0 and 49151");
            this.port = 9090;
        } else {
            this.port = port;
        }
    }

    public TCPMultiServer() {
        this.port = 9090;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Server is listening on port " + this.port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.put(socket.getInetAddress().getHostAddress(), clientHandler);
                clientHandler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;
        private PrintWriter printOut;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                this.printOut = out;
                ChatProtocolParser parser = new ChatProtocolParser();
                String clientMessage;

                String nickname = socket.getInetAddress().getHostAddress();
                TCPMultiServer.clients.put(nickname, this);

                try {
                    while ((clientMessage = in.readLine()) != null) {
                        ChatProtocolParser.ParsedCommand parsedMessage = parser.parseLine(clientMessage);
                        if (parsedMessage.type == ChatProtocolParser.CommandType.MESSAGE) {
                            if ("exit".equalsIgnoreCase(parsedMessage.value)) {
                                System.out.println("Client disconnected");
                                break;
                            } else {
                                System.out.println("Received from client " + nickname + ", Message: " + clientMessage);
                                out.println("message received");
                            }
                        } else if (parsedMessage.type == ChatProtocolParser.CommandType.NICKNAME) {
                            System.out.println("User: " + nickname + " will be renamed to " + parsedMessage.value);
                            TCPMultiServer.clients.remove(nickname);
                            nickname = parsedMessage.value;
                            TCPMultiServer.clients.put(nickname, this);
                            out.println("Successfully changed nickname to " + nickname);

                        } else if (parsedMessage.type == ChatProtocolParser.CommandType.MENTION) {
                            String mentionedUser = parsedMessage.value;
                            ClientHandler mentionedClient = TCPMultiServer.clients.get(mentionedUser);
                            if (mentionedClient != null) {
                                if (mentionedClient.sendMessage("Message from " + nickname + ": " + parsedMessage.message)) {
                                    out.println("Message sent to " + mentionedUser);
                                } else {
                                    out.println("Error, " + mentionedUser + " is not reachable");
                                }
                            } else {
                                out.println("Error, nobody with the name " + mentionedUser + " is connected");
                            }
                        }
                    }
                } finally {
                    System.out.println("Closing the connection");
                    TCPMultiServer.clients.remove(nickname);
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Boolean sendMessage(String message) {
            if (this.printOut != null) {
                try {
                    this.printOut.println(message);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        TCPMultiServer server;
        if (args.length < 1) {
            server = new TCPMultiServer();
        } else {
            int port = Integer.parseInt(args[0]);
            server = new TCPMultiServer(port);
        }
        server.start();
    }
}