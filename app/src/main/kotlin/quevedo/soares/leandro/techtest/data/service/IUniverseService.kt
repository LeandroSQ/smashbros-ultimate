package quevedo.soares.leandro.techtest.data.service

import quevedo.soares.leandro.techtest.domain.model.Universe
import retrofit2.http.GET

interface IUniverseService {

	@GET("/universes")
	suspend fun getUniverses(): List<Universe>

}