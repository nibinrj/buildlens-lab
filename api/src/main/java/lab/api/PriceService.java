package lab.api;

import lab.core.Calculator;

/** Uses core to total an order, so api depends on core like a real multi-module project. */
public final class PriceService {

    private final Calculator calculator = new Calculator();

    public int total(int[] prices) {
        int sum = 0;
        for (int price : prices) {
            sum = calculator.add(sum, price);
        }
        return sum;
    }
}
