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
	@Json(name = "imageURL") val avatar: String? = null,
) {

	fun isContentEqualTo(other: Fighter) = this.id == other.id &&
			this.name == other.name &&
			this.universe == other.universe &&
			this.price == other.price &&
			this.popular == other.popular &&
			this.rate == other.rate &&
			this.downloads == other.downloads &&
			this.description == other.description &&
			this.created_at == other.created_at

}