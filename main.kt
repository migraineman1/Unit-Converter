import kotlin.reflect.typeOf

//package converter

// base measurements are grams, meters, and degrees celsius
enum class Measurements(val abbreviation: String, val singular: String, val plural: String, val category: String, val toBase: (Double) -> Double, val fromBase: (Double) -> Double) {
    G("g", "gram", "grams", "Weight", { x -> x }, {x -> x }),
    KG("kg", "kilogram", "kilograms", "Weight", { x -> x * 1000 }, {x -> x / 1000 }),
    MG("mg", "milligram", "milligrams", "Weight", { x -> x / 1000 }, {x -> x * 1000 }),
    LB("lb", "pound", "pounds", "Weight", { x -> x * 453.592 }, {x -> x / 453.592 }),
    OZ("oz", "ounce", "ounces", "Weight", { x -> x * 28.3495 }, {x -> x / 28.3495 }),
    M("m", "meter", "meters", "Length", { x -> x }, {x -> x }),
    KM("km", "kilometer", "kilometers", "Length", { x -> x * 1000 }, {x -> x / 1000 }),
    CM("cm", "centimeter", "centimeters", "Length", { x -> x / 100 }, {x -> x * 100 }),
    MM("mm", "millimeter", "millimeters", "Length", { x -> x / 1000 }, {x -> x * 1000 }),
    MI("mi", "mile", "miles", "Length", { x -> x * 1609.35 }, { x -> x / 1609.35 }),
    YD("yd", "yard", "yards", "Length", { x -> x * 0.9144 }, { x -> x / 0.9144 }),
    FT("ft", "foot", "feet", "Length", { x -> x * 0.3048 }, { x -> x / 0.3048 }),
    IN("in", "inch", "inches", "Length", { x -> x * 0.0254 }, { x -> x / 0.0254 }),
    C("dc", "degree celsius", "degrees celsius", "temperature", { x -> x }, {x -> x }),
    F("df", "degree fahrenheit", "degrees fahrenheit", "temperature", { x -> (x - 32) * 5 / 9 }, {x -> (x * 9 / 5) + 32 }),
    K("k", "kelvin", "kelvins", "temperature", { x -> x - 273.15 }, {x -> x + 273.15 });


    companion object{
        fun findMeasurement(unit: String?): Measurements? {
            for (measurement in Measurements.entries) {
                if (unit == measurement.abbreviation || unit == measurement.singular || unit == measurement.plural || unit!!.uppercase() == measurement.toString()) return measurement
            }
            return null
        }

        fun isCompatible(unit1: Measurements?, unit2: Measurements?): Boolean {
            if (unit1!!.category == unit2!!.category) return true
            else return false
        }

        fun convert(unit1: Measurements, size: Double, unit2: Measurements): Double {
            val firstStep = unit1.toBase(size)
            val result = unit2.fromBase(firstStep)
            return result
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

        fun printError(unit1: Measurements?, size: Double, unit2: Measurements?) {
            println(buildString {
                append("Conversion from ")
                if (unit1 == null) append("???")
                     else append(unit1.plural)
                append(" to ")
                if (unit2 == null) append("???")
                     else append(unit2.plural)
                append(" is impossible")
            } )
        }

    }   // companion object

}    // enum class

// function to get input
fun getInput(): String? {
    print("Enter what you want to convert (or exit): ")
    var input: String? = readln().lowercase()
    if (input == null) input = "exit"
    return input
}


fun main() {
    // handle initial input
    var size1 = 0.0
    var input = getInput()

    // process input
    while (!input!!.contains("exit")) {
        var data = input?.split(" ")
        var index = 0

        // check for non numerical input and re-prompt for input
        try {
            size1 = data!![index++].toDouble()
        } catch (e: NumberFormatException) {
            println("Parse error")

            input = getInput()
            continue
        }

        var unit: String

        // check for 2 word measurement name
        if (data!![index] == "degrees" || data!![index] == "degree") unit = buildString {
            append(data!![index++])
            append(" ")
            append(data!![index++])
        }
        else unit = data!![index++]
        if (unit == "fahrenheit" || unit == "celsius") unit = buildString {
            append("degrees ")
            append(unit)
        }
        val unit1: Measurements? = Measurements.findMeasurement(unit)

        // skip next input entry
        index++

        // check for 2 word measurement name
        if (data!![index] == "degrees" || data!![index] == "degree") unit = buildString {
            append(data!![index++])
            append(" ")
            append(data!![index++])
        }
        else unit = data!![index++]
        if (unit == "fahrenheit" || unit == "celsius") unit = buildString {
            append("degrees ")
            append(unit)
        }
        val unit2: Measurements? = Measurements.findMeasurement(unit)



        if (unit1 == null || unit2 == null) Measurements.printError(unit1, size1, unit2)
        // check for compatability of measurements i.e. length != weight
        else {
            // check for negative weight or distance
            if (size1 < 0 && (unit1.category == "Length" || unit1.category == "Weight")) {
                println("${unit1.category} shouldn't be negative")
                input = getInput()
                continue
            }
            if (Measurements.isCompatible(unit1, unit2)) {
                var size2 = Measurements.convert(unit1!!, size1, unit2!!)
                Measurements.printString(unit1, size1, unit2, size2)
            } else Measurements.printError(unit1, size1, unit2)
            println("")

        }
            input = getInput()
    }
}