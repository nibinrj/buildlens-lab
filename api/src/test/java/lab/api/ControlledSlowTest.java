package lab.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// INJECTED controlled delay for the BuildLens regression demonstration.
// This is a duration signal, never a naturally occurring performance finding.
class ControlledSlowTest {

    @Test
    void controlledWork() throws InterruptedException {
        long milliseconds = Long.parseLong(System.getProperty("buildlens.slow.ms", "0"));
        assertTrue(milliseconds >= 0 && milliseconds <= 10000,
                "buildlens.slow.ms must be between 0 and 10000");
        Thread.sleep(milliseconds);
        assertEquals(60, new PriceService().total(new int[] {10, 20, 30}));
    }
}
