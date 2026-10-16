package lab.flaky;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

// INJECTED flakiness: a simulated expiry boundary. No wall-clock sleep or host time dependency.
class TimeTest {
    private static final long BASE = Long.getLong("buildlens.flaky.clockMillis", 1000L);
    private static int attempts;
    @Test void expiresAtBoundary() {
        Clock clock = Clock.fixed(Instant.ofEpochMilli(BASE + attempts++ * 1000L), ZoneOffset.UTC);
        assertTrue(Math.floorMod(clock.millis(), 5000L) != 0,
                "INJECTED expiry at a simulated five-second boundary");
    }
}
