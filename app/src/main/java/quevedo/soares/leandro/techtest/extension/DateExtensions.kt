package quevedo.soares.leandro.techtest.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Tries to parse date from a given string, returning null in case of error or invalid
 * @see [tryFormat]
 *
 * @return The parsed date, null in case of error or invalid
 **/
fun SimpleDateFormat.tryParse(input: String) = try {
	this.parse(input)
} catch (e: Exception) {
	null
}

/**
 * Tries to format a given [java.util.Date], returning null in case of error
 * @see [tryParse]
 *
 * @return The formatted date, null in case of error
 **/
fun SimpleDateFormat.tryFormat(input: Date) = try {
	this.format(input)
} catch (e: Exception) {
	null
}