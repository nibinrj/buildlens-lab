package lab.flaky;

import java.util.SplittableRandom;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

// INJECTED flakiness: seeded PRNG, target 20% failure per invocation, including reruns.
class RandomTest {
    private static final SplittableRandom RANDOM =
            new SplittableRandom(Long.getLong("buildlens.flaky.seed", 1L));
    @Test void sometimesFails() {
        double draw = RANDOM.nextDouble();
        assertTrue(draw >= 0.20, () -> "INJECTED random draw below policy threshold: " + draw);
    }
}
