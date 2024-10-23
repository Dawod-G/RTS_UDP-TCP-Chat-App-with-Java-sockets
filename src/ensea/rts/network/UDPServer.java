package ensea.rts.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private final int listeningPort;
    private static final int maxBytes = 1024;
    private final byte[] buffer = new byte[maxBytes];

    /**
     * Constructor to initialize UDPServer with a specific port.
     *
     * @param listeningPort The port on which the server listens.
     */
    public UDPServer(int listeningPort) {
        this.listeningPort = listeningPort;
    }

    public UDPServer() {
        this.listeningPort = 70;
    }

    public void launch() throws IOException {
        DatagramSocket socket = new DatagramSocket(listeningPort);
        System.out.println("Server started on port: " + listeningPort);

        // TODO: maybe add a timeout depending of the state of the server
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength(), "UTF-8");

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();

            System.out.println("Received from " + clientAddress + ":" + clientPort + " - " + received);
        }
    }

    @Override
    public String toString() {
        return "UDPServer listening on port: " + listeningPort;
    }

    /**
     * Main method to run the UDPServer.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws IOException {
        int port = 70;
        if (args.length > 0) {
            UDPServer server = new UDPServer(port);
            server.launch();
        } else {
            UDPServer server = new UDPServer();
            server.launch();
        }
    }
}