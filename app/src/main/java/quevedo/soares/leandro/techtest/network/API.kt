package quevedo.soares.leandro.techtest.network

import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.domain.model.Universe
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

	@GET("/universes")
	fun getUniverses(): List<Universe>

	@GET("/fighters")
	fun getFighters(
		@Query("universe") universe: Universe? = null
	): List<Fighter>

}