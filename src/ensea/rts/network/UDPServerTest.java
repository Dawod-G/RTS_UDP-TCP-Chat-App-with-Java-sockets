package ensea.rts.network;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UDPServerTest {

    @Test
    public void testToString() {
        UDPServer server = new UDPServer(8080);
        String expected = "UDPServer listening on port 8080";
        assertEquals(expected, server.toString());
    }

    @Test
    public void testDefaultConstructor() {
        UDPServer server = new UDPServer();
        String expected = "UDPServer listening on port 70";
        assertEquals(expected, server.toString());
    }
}