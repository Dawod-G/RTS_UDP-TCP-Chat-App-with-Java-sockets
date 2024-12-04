package ensea.rts.udp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

/**
 * Unit tests for the UDPClient class.
 */
public class UDPClientTest {

    /**
     * Tests the main method of the UDPClient class when arguments are missing.
     * Expects an IOException to be thrown with a specific message.
     */
    @Test
    public void testMainMethodWithMissingArguments() {
        String[] args = {};
        Exception exception = assertThrows(IOException.class, () -> {
            UDPClient.main(args);
        });
        assertEquals("Missing arguments \"server\" or \"port\".", exception.getMessage());
    }
}