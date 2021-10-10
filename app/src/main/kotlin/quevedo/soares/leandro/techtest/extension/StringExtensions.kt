package quevedo.soares.leandro.techtest.extension

import java.text.NumberFormat
import java.util.*

/**
 * Formats an string-integer
 * Ex: "12312" -> "12,312"
 *
 * @return The formatted string
 **/
fun String?.toFormattedInt(): String {
	if (this.isNullOrEmpty()) return ""

	return try {
		NumberFormat.getIntegerInstance(Locale.getDefault()).format(this.toInt())
	} catch (e: Exception) {
		this
	}
}
