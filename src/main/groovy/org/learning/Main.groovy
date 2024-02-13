package org.learning

import groovy.transform.EqualsAndHashCode

static void main(String[] args) {
    println "Hello world!"
    typing()
    oop()
    dataTypes()
    errorHandling()
    integerCalculator()
    floatCalculator()
}

private static void integerCalculator() {
    Calculable<Integer> integerCalculable = new IntegerCalculator(2)
    integerCalculable.addition(2)
    integerCalculable.multiply(3)
    integerCalculable.subtraction(1)
    integerCalculable.divide(2)
    println integerCalculable
}

private static void floatCalculator() {
    Calculable<Float> floatCalculable = new FloatCalculator(2)
    floatCalculable.addition(2)
    floatCalculable.multiply(3)
    floatCalculable.subtraction(1)
    floatCalculable.divide(2)
    println floatCalculable
}


private static void oop() {
    def equals_1 = new MethodGenerationFromAnnotation();
    def equals_2 = new MethodGenerationFromAnnotation();
    println "HashCode Equals: " + (equals_1 == equals_2)
}

private static void typing() {
    String msg = "Groovy Str"
    def duck_typing = "Implicit Groovy Str"

    println msg
    println duck_typing
}

@EqualsAndHashCode
class MethodGenerationFromAnnotation {}

private static void dataTypes() {
    int age = 40
    println age.getClass()

    def name = "John"
    println name.getClass()

    Person johnDoe = new Person()
    johnDoe.setFirstName("John")
    johnDoe.setLastName("Doe")
    johnDoe.setAge(40)

    println(johnDoe.getFullName())
    println(johnDoe.getAge())

    if (johnDoe.getAge() <= 45 && johnDoe.getAge() <= 65)
        println johnDoe.getFullName() + " is middle aged"
    else
        println johnDoe.getFullName() + " is " + johnDoe.getAge() + " years old"


    def persons = [johnDoe, new Person(firstName: "Mary", lastName: "Mill", age: 55)];

    for ( Person person: persons )
        println person
}

class Person {
    String firstName
    String lastName
    int age
    String getFullName() {
        firstName + " " + lastName
    }

    @Override
    String toString() {
        return getFullName()
    }
}

private static void errorHandling() {
    final Person johnDoe = new Person()
    johnDoe.setFirstName("John")
    johnDoe.setLastName("Doe")
    johnDoe.setAge(40)

    try {
        johnDoe.getFirstName().toLong()
    } catch (NumberFormatException error) {
        println "caught error message"
    }
}