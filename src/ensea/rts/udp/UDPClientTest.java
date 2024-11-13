package ensea.rts.udp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class UDPClientTest {

    @Test
    public void testMainMethodWithMissingArguments() {
        String[] args = {};
        Exception exception = assertThrows(IOException.class, () -> {
            UDPClient.main(args);
        });
        assertEquals("Missing arguments \"server\" or \"port\".", exception.getMessage());
    }
}