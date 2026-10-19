package lab.flaky;

import java.util.Collections;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.assertFalse;

// INJECTED flakiness: polluter leaks shared state; victim fails only when it runs afterwards.
@TestMethodOrder(OrderTest.SeededOrder.class)
class OrderTest {
    private static boolean polluted;
    @Test void polluter() { polluted = true; }
    @Test void victim() {
        boolean observed = polluted;
        polluted = false; // A retry sees clean state; retain the first failure in Surefire's XML.
        assertFalse(observed, "INJECTED leaked state from polluter");
    }
    public static class SeededOrder implements MethodOrderer {
        @Override public void orderMethods(MethodOrdererContext context) {
            long seed = Long.getLong("buildlens.flaky.seed", 1L);
            Collections.shuffle(context.getMethodDescriptors(), new java.util.Random(seed * 0x9e3779b97f4a7c15L));
        }
    }
}
