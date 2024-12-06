//package converter

// base measurements are grams, meters, and degrees celsius
enum class Measurements(val abbreviation: String, val singular: String, val plural: String, val category: String, val toBase: (Double) -> Double, val fromBase: (Double) -> Double) {
    G("g", "gram", "grams", "weight", { x -> x }, {x -> x }),
    KG("kg", "kilogram", "kilograms", "weight", { x -> x * 1000 }, {x -> x / 1000 }),
    MG("mg", "milligram", "milligrams", "weight", { x -> x / 1000 }, {x -> x * 1000 }),
    LB("lb", "pound", "pounds", "weight", { x -> x / 453.592 }, {x -> x * 453.592 }),
    OZ("oz", "ounce", "ounces", "weight", { x -> x / 28.3495 }, {x -> x * 28.3495 }),
    M("m", "meter", "meters", "length", { x -> x }, {x -> x }),
    KM("km", "kilometer", "kilometers", "length", { x -> x * 1000 }, {x -> x / 1000 }),
    CM("cm", "centimeter", "centimeters", "length", { x -> x / 100 }, {x -> x * 100 }),
    MM("mm", "millimeter", "millimeters", "length", { x -> x / 1000 }, {x -> x * 1000 }),
    MI("mi", "mile", "miles", "length", { x -> x * 1609.35 }, { x -> x / 1609.35 }),
    YD("yd", "yard", "yards", "length", { x -> x * 0.9144 }, { x -> x / 0.9144 }),
    FT("ft", "foot", "feet", "length", { x -> x * 0.3048 }, { x -> x / 0.3048 }),
    IN("in", "inch", "inches", "length", { x -> x * 0.0254 }, { x -> x / 0.0254 }),
    C("dc", "degree celsius", "degrees celsius", "temperature", { x -> x }, {x -> x }),
    F("df", "degree fahrenheit", "degrees fahrenheit", "temperature", { x -> (x - 32) * 5 / 9 }, {x -> (x * 9 / 5) + 32 }),
    K("k", "degree kelvin", "degrees kelvin", "temperature", { x -> x - 273.15 }, {x -> x + 273.15 });


    companion object{
    fun findMeasurement(input: String): Measurements? {
        for (measurement in Measurements.entries) {
            if (input == measurement.abbreviation || input == measurement.singular || input == measurement.plural) return measurement
        }
        return null
    }

    fun printString(unit1: Measurements, length: Double, unit2: Measurements, result: Double) {
        val  measure = Measurements
        println(buildString {
            append(length)
            append(" ")
            if (length == 1.0) append(unit1.singular)
            else append(unit1.plural)
            append(" is ")
            append(result)
            append(" ")
            if (result == 1.0) append(unit2.singular)
            else append(unit2.plural)
        } )
    }

    }

}

//fun printString(): Unit {}

fun main() {
    print("Enter a number and a measure of length:")
    val input = readln().split(" ")
    val measure = Measurements.findMeasurement(input[1].lowercase())
    if (measure == null) println("\nWrong input. Unknown unit ${input[1]}")
    else {
        val length = input[0].toDouble()
        val result = measure.toBase.let { it(length) }
        Measurements.printString(measure, length, Measurements.M, result)
    }
}
