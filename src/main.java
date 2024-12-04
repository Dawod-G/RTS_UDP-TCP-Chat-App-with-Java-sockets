import java.io.IOException;
import java.util.Arrays;

import ensea.rts.tcp.*;
import ensea.rts.udp.*;

public class main {
    /**
     * The main method that serves as the entry point for the application.
     * It takes command-line arguments to determine the mode of operation.
     *
     * @param args Command-line arguments where the first argument specifies the mode.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java -jar app.jar <mode> [options]");
            System.err.println("Modes: tcp-client, tcp-server, tcp-multiserver, udp-client, udp-server");
            return;
        }

        String mode = args[0];
        switch (mode) {
            case "tcp-client":
                // Start the TCP client with the remaining arguments
                TCPClient.main(args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;
            case "tcp-server":
                // Start the TCP server with the remaining arguments
                TCPServer.main(args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;
            case "tcp-multiserver":
                // Start the TCP multi-server with the remaining arguments
                TCPMultiServer.main(args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;
            case "udp-client":
                // Start the UDP client with the remaining arguments
                UDPClient.main(args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;
            case "udp-server":
                // Start the UDP server with the remaining arguments
                UDPServer.main(args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;
            default:
                // Print an error message for an unknown mode
                System.err.println("Unknown mode: " + mode);
        }
    }
}