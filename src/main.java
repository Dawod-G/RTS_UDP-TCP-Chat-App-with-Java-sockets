import java.io.IOException;

import ensea.rts.tcp.TCPClient;
import ensea.rts.tcp.TCPServer;
import ensea.rts.tcp.TCPMultiServer;
import ensea.rts.udp.UDPClient;
import ensea.rts.udp.UDPServer;

/**
 * The Main class serves as the entry point for the application.
 */
public class main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java -jar app.jar <mode> [options]");
            System.err.println("Modes: tcp-client, tcp-server, tcp-multiserver, udp-client, udp-server");
            return;
        }

        String mode = args[0];
        switch (mode) {
            case "tcp-client":
                TCPClient.main(args.length > 1 ? java.util.Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;
            case "tcp-server":
                TCPServer.main(args.length > 1 ? java.util.Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;
            case "tcp-multiserver":
                TCPMultiServer.main(args.length > 1 ? java.util.Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;

            case "udp-client":
                UDPClient.main(args.length > 1 ? java.util.Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;
            case "udp-server":
                UDPServer.main(args.length > 1 ? java.util.Arrays.copyOfRange(args, 1, args.length) : new String[]{});
                break;

            default:
                System.err.println("Unknown mode: " + mode);
        }
    }
}