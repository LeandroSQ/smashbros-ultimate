package quevedo.soares.leandro.techtest.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Fighter(
	@Json(name = "objectID") val id: String? = null,
	@Json(name = "name") val name: String? = null,
	@Json(name = "universe") val universe: String? = null,
	@Json(name = "price") val price: String? = null,
	@Json(name = "popular") val popular: Boolean = false,
	@Json(name = "rate") val rate: UInt = 0u,
	@Json(name = "downloads") val downloads: String? = null,
	@Json(name = "description") val description: String? = null,
	@Json(name = "created_at") val created_at: Date? = null,
)