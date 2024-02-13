package org.learning

class FloatCalculator implements Calculable<Float> {

    public FloatCalculator(Float initialValue) {
        this.setNumber initialValue
    }

    @Override
    String toString() {
        return "Value is " + this.getNumber()
    }
}
