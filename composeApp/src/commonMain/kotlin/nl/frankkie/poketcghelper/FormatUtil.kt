package nl.frankkie.poketcghelper

import kotlin.math.min


object FormatUtil {

    fun floatFormat(value: Float): String {
        val asString = value.toString()
        val indexOfDecimal = asString.indexOf('.')
        if (indexOfDecimal == -1) {
            return asString
        }
        return asString.substring(0, min(indexOfDecimal + 2, asString.length))
    }


}

fun test() {
    println(FormatUtil.floatFormat(100.0f))
    println(FormatUtil.floatFormat(1.0f))
    println(FormatUtil.floatFormat(1.2f))
    println(FormatUtil.floatFormat(1.234567f))
    println(FormatUtil.floatFormat(1.23456789f))
    println(FormatUtil.floatFormat(1.234567890123456789f))
    println(FormatUtil.floatFormat(1.2345678901234567890123456789f))
}