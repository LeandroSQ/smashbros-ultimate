package quevedo.soares.leandro.techtest.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.android.parcel.Parcelize
import java.util.*

@JsonClass(generateAdapter = true)
@Entity
@Parcelize
data class Fighter(
	@Id var id: Long = 0L,// ObjectBox - id

	@Json(name = "objectID") val objectId: String? = null,
	@Json(name = "name") val name: String? = null,
	@Json(name = "universe") val universe: String? = null,
	@Json(name = "price") val price: String? = null,
	@Json(name = "popular") val popular: Boolean = false,
	@Json(name = "rate") val rate: Int = 0,
	@Json(name = "downloads") val downloads: String? = null,
	@Json(name = "description") val description: String? = null,
	@Json(name = "created_at") val created_at: Date? = null,
	@Json(name = "imageURL") val avatar: String? = null,
) : Parcelable {

	fun isContentEqualTo(other: Fighter) = this.objectId == other.objectId &&
			this.name == other.name &&
			this.universe == other.universe &&
			this.price == other.price &&
			this.popular == other.popular &&
			this.rate == other.rate &&
			this.downloads == other.downloads &&
			this.description == other.description &&
			this.created_at == other.created_at

}