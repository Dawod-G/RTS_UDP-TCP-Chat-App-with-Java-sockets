package ensea.rts.udp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UDPServer class.
 */
public class UDPServerTest {

    /**
     * Tests the toString method of the UDPServer class.
     */
    @Test
    public void testToString() {
        UDPServer server = new UDPServer(8080);
        String expected = "UDPServer listening on port 8080";
        assertEquals(expected, server.toString());
    }

    /**
     * Tests the default constructor of the UDPServer class.
     */
    @Test
    public void testDefaultConstructor() {
        UDPServer server = new UDPServer();
        String expected = "UDPServer listening on port 70";
        assertEquals(expected, server.toString());
    }
}