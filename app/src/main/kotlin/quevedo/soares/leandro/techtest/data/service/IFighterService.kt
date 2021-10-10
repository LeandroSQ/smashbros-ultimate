package quevedo.soares.leandro.techtest.data.service

import quevedo.soares.leandro.techtest.domain.model.Fighter
import retrofit2.http.GET
import retrofit2.http.Query

interface IFighterService {

	@GET("/fighters")
	suspend fun getFighters(
		@Query("universe") universeName: String? = null
	): List<Fighter>

}