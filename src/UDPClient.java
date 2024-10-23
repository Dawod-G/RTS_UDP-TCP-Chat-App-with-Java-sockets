import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    /**
     * Main method to run the UDPClient.
     *
     * @param args Command line arguments: server address and port.
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Missing arguments \"server\" or \"port\".");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverInetAddress = InetAddress.getByName(serverAddress);

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String inputLine;

        System.out.println("Enter the text to send to the server.");

        while ((inputLine = userInput.readLine()) != null) {
            if ("exit".equalsIgnoreCase(inputLine)) {
                break;
            }

            byte[] buffer = inputLine.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverInetAddress, serverPort);
            socket.send(packet);
        }
        socket.close();
    }
}