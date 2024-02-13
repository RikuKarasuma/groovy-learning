package org.learning

trait Calculable<N> {

    N number

    def addition(N numberTwo) {
        this.setNumber(this.addition this.number, numberTwo)
        this
    }

    static N addition(N... numbers) {
        numbers.sum()
    }

    def subtraction(N numberTwo) {
        this.setNumber(this.subtraction this.number, numberTwo)
        this
    }

    static N subtraction(N numberOne, N numberTwo) {
        numberOne - numberTwo
    }

    def multiply(N numberTwo) {
        this.setNumber(this.multiply this.number, numberTwo)
        this
    }

    static N multiply(N... numbers) {
        def answer = numbers[0]
        for ( int i = 1; i < numbers.length; i ++) {
            answer *= numbers[1]
        }
        answer
    }

    def divide(N numberTwo) {
        this.setNumber(this.divide this.number, numberTwo)
    }

    static N divide(N numberOne, N numberTwo) {
        if (numberOne == 0 || numberTwo == 0)
            throw new IllegalArgumentException("Cannot divide by Zero!")

        numberOne / numberTwo
    }
}