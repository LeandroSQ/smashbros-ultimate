@file:SuppressLint("SimpleDateFormat")

package quevedo.soares.leandro.techtest.converter

import android.annotation.SuppressLint
import com.squareup.moshi.*
import quevedo.soares.leandro.techtest.util.tryFormat
import quevedo.soares.leandro.techtest.util.tryParse
import java.text.SimpleDateFormat
import java.util.*

private const val SERVER_DATE_FORMAT = "E MMM d yyyy HH:mm:ss 'GMT'Z"
private val dateFormatter by lazy { SimpleDateFormat(SERVER_DATE_FORMAT) }

class MoshiJsonDateConverter : JsonAdapter<Date?>() {

	@FromJson
	override fun fromJson(reader: JsonReader): Date? {
		val input = reader.nextString()
		synchronized(dateFormatter) {
			return dateFormatter.tryParse(input)
		}
	}

	@ToJson
	override fun toJson(writer: JsonWriter, value: Date?) {
		synchronized(dateFormatter) {
			if (value != null) {
				writer.value(dateFormatter.tryFormat(value))
			} else {
				writer.nullValue()
			}
		}
	}

}