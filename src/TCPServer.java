import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private final int listeningPort;
    private static final int maxBytes = 1024;
    private final byte[] buffer = new byte[maxBytes];

    public TCPServer(int listeningPort) {
        this.listeningPort = listeningPort;
    }

    public TCPServer() {
        this.listeningPort = 70;
    }

    public void launch() throws IOException {
        ServerSocket serverSocket = new ServerSocket(listeningPort);
        // TODO: find a way to wait for a connection
        Socket socket = serverSocket.accept();
    }
}