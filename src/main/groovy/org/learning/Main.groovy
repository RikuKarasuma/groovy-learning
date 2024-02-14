package org.learning

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import java.nio.file.Files
import java.nio.file.Path

static void main(String[] args) {
    println "Hello world!"
    typing()
    ast_abstract_syntax_tree()
    dataTypes()
    errorHandling()
    integerCalculator()
    floatCalculator()
    closure()
    collections()
    files_io()
    files_io_writing()
    challengeProcessingNumbers()
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


private static void ast_abstract_syntax_tree() {
    final Person johnDoe = new Person(firstName: "John", lastName: "Doe", age: 40)
    final Person johnDoe_1 = new Person(firstName: "John", lastName: "Doe", age: 39)
    final Person maryBaley = new Person(firstName: "Mary", lastName: "Baley", age: 35)
    println "HashCode Equals: " + (johnDoe == johnDoe_1) + " | " + (johnDoe == maryBaley)
}

private static void typing() {
    String msg = "Groovy Str"
    def duck_typing = "Implicit Groovy Str"

    println msg
    println duck_typing
}


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

@EqualsAndHashCode(includeFields = true, excludes = ['age'])
@ToString
@TupleConstructor
//@Canonical // Auto includes above three
class Person implements Serializable {
    String firstName
    String lastName
    int age
    String getFullName() {
        firstName + " " + lastName
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

private static void closure() {
    final Person johnDoe = new Person()
    johnDoe.setFirstName("John")
    johnDoe.setLastName("Doe")
    johnDoe.setAge(40)

    Closure personHashCode = { Person person ->
        println person.hashCode()
    }

    personHashCode(johnDoe)
}

private static void collections() {
    final Person johnDoe = new Person(firstName: "John", lastName: "Doe", age: 40)
    final Person johnDoe_1 = new Person(firstName: "John", lastName: "Doe", age: 39)
    final Person maryBaley = new Person(firstName: "Mary", lastName: "Baley", age: 35)

    def allPersons = [johnDoe, johnDoe_1, maryBaley]

    assert allPersons instanceof List
    assert allPersons.size() == 3
    assert allPersons[2] == maryBaley

    // Iterate over persons
    allPersons.each {
        println it
    }

    // Iterate over persons with index
    allPersons.eachWithIndex { Person entry, int i ->
        println entry.toString() + " at " + i + " index"
    }

    // Filter for specific
    allPersons.find { it.lastName == 'Baley' } == maryBaley

    // Transform
    allPersons.collect { it.age <= 35 } == [false, false, true]

    // Sorting elements based on criteria
    println allPersons.sort { it.age }
}

private static void files_io() {
    Person personFromFile = new Person()

    final sourcePath = Main.class.classLoader.getResource("./")?.path
    final resourcePath = sourcePath.substring(0, sourcePath.indexOf("groovy/")) + "resources/"
    // Read full contents.
    File file = new File(resourcePath + "Person.txt")
    println file.getText("UTF-8")

    // Iterate over lines
    file.eachLine {line, num ->
        if (num == 1)
            personFromFile.setFirstName(line)
        else if (num == 2)
            personFromFile.setLastName(line)
        else if (num == 3)
            personFromFile.setAge(line.toInteger())
    }
    println personFromFile
}

private static void files_io_writing() {
    final Person personToFile = new Person(firstName: "Mary", lastName: "Baley", age: 35)

    final sourcePath = Main.class.classLoader.getResource("./")?.path
    // Remove excess path information
    final resourcePath = sourcePath.substring(0, sourcePath.indexOf("groovy/")) + "resources/"
    println "File Path:" + resourcePath
    // Write to file.
    final File file = new File(resourcePath + "mary-baley.txt")
    file.withWriter {
        it.writeLine personToFile.firstName
        it.writeLine personToFile.lastName
        it.writeLine personToFile.age.toString()
    }

    file.append("2")
    file << "3"

    // Serialization

    final File byteCodeFile = new File(resourcePath + "java_bytecode_file.bin")
    byteCodeFile.withObjectOutputStream {
        it.writeObject(personToFile)
    }
}

/**
 * -> Read all lines of every file found in the "resources"
 *    directory.
 *
 * -> Capture all numbers in a list.
 *       -> Omit other entries.
 *
 * -> Determine highest number in List and print it.
 *
 * -> Add all number in List and print sum.
 */
private static void challengeProcessingNumbers() {

    final sourcePath = Main.class.classLoader.getResource("./")?.path
    // Remove excess path information
    final resourcePath = sourcePath.substring(0, sourcePath.indexOf("groovy/")) + "resources/"
    println "Resources Path:" + resourcePath

    final List<Path> resources = Files.list(Path.of(resourcePath)).collect()
    final List<Integer> numbers = []

    resources.each {path ->

        def file = new File(path.toString())

        numbers.addAll file.readLines().collect {

            try {
                it?.toInteger()
            } catch (NumberFormatException errorToIgnore) {
                println "Not a number line"
                // do nothing
            }
        }.findAll { it != null }

    }

    numbers.sort {itOne, itTwo ->
        if (itOne == itTwo)
            return 0
        else if (itOne < itTwo)
            return 1
        else if (itOne > itTwo)
            return -1
    }

    println "Highest number: " + numbers[0]


    println "Sum: " + numbers.sum()

}

