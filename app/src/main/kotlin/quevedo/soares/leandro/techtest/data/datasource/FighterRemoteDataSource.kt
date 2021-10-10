package quevedo.soares.leandro.techtest.data.datasource

import quevedo.soares.leandro.techtest.data.service.IFighterService

class FighterRemoteDataSource(private val service: IFighterService) {

	suspend fun getUniverses(universeName: String? = null) = service.getFighters(universeName)

}