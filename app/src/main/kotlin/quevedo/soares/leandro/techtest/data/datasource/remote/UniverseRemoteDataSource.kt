package quevedo.soares.leandro.techtest.data.datasource.remote

import quevedo.soares.leandro.techtest.data.service.IUniverseService

class UniverseRemoteDataSource(private val service: IUniverseService) {

	suspend fun getUniverses() = service.getUniverses()

}