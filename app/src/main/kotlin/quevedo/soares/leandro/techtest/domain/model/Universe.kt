package quevedo.soares.leandro.techtest.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@JsonClass(generateAdapter = true)
@Entity
data class Universe(
	@Id var id: Long = 0L,// ObjectBox - id

	@Json(name = "objectID") val objectId: String? = null,
	@Json(name = "name") val name: String? = null,
	@Json(name = "description") val description: String? = null,
)