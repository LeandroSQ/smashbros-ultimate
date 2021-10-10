package quevedo.soares.leandro.techtest.data.repository

import quevedo.soares.leandro.techtest.data.datasource.UniverseRemoteDataSource

class UniverseRepository(private val dataSource: UniverseRemoteDataSource) {

	suspend fun getUniverses() = dataSource.getUniverses()

}