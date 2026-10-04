package lab.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// Stable tests: these should pass on every build.
class PriceServiceTest {

    @Test
    void totalsPrices() {
        assertEquals(60, new PriceService().total(new int[] {10, 20, 30}));
    }

    @Test
    void emptyOrderIsZero() {
        assertEquals(0, new PriceService().total(new int[0]));
    }
}
