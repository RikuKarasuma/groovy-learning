package org.learning

class IntegerCalculator implements Calculable<Integer> {

    public IntegerCalculator(Integer initialValue) {
        this.setNumber initialValue
    }

    @Override
    String toString() {
        return "Value is " + this.getNumber()
    }
}
