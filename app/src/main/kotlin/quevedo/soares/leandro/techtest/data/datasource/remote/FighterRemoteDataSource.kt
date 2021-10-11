package quevedo.soares.leandro.techtest.data.datasource.remote

import quevedo.soares.leandro.techtest.data.service.IFighterService

class FighterRemoteDataSource(private val service: IFighterService) {

	suspend fun getFighters(universeName: String? = null) = service.getFighters(universeName)

}