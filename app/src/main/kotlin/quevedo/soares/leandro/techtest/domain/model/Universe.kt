package quevedo.soares.leandro.techtest.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Universe(
	@Json(name = "objectID") val id: String? = null,
	@Json(name = "name") val name: String? = null,
	@Json(name = "description") val description: String? = null,

	@Transient var isSelected: Boolean = false
)