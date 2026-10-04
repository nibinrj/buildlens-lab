package lab.core;

/** Plain arithmetic: the stable code the lab's tests exercise. */
public final class Calculator {

    public int add(int a, int b) {
        return Math.addExact(a, b);
    }

    public int divide(int dividend, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("divisor must not be zero");
        }
        return dividend / divisor;
    }
}
