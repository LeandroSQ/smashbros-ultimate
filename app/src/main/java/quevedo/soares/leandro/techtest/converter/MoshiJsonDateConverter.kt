@file:SuppressLint("SimpleDateFormat")

package quevedo.soares.leandro.techtest.converter

import android.annotation.SuppressLint
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import quevedo.soares.leandro.techtest.util.tryFormat
import quevedo.soares.leandro.techtest.util.tryParse
import java.text.SimpleDateFormat
import java.util.*

private const val SERVER_DATE_FORMAT = "E d MMM yyyy HH:mm:ss 'GMT'Z"
private val dateFormatter by lazy { SimpleDateFormat(SERVER_DATE_FORMAT) }

class MoshiJsonDateConverter: JsonAdapter<Date>() {

	override fun fromJson(reader: JsonReader): Date? {
		val input = reader.nextString()
		return dateFormatter.tryParse(input)
	}

	override fun toJson(writer: JsonWriter, value: Date?) {
		if (value != null) {
			writer.value(dateFormatter.tryFormat(value))
		} else {
			writer.nullValue()
		}
	}

}