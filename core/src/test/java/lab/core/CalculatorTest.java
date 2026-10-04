package lab.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// Stable tests: these should pass on every build.
class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void addsNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void addRejectsOverflow() {
        assertThrows(ArithmeticException.class, () -> calculator.add(Integer.MAX_VALUE, 1));
    }

    @Test
    void dividesNumbers() {
        assertEquals(4, calculator.divide(12, 3));
    }

    @Test
    void divideRejectsZero() {
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(1, 0));
    }
}
