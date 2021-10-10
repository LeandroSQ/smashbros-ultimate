package quevedo.soares.leandro.techtest.data.repository

import quevedo.soares.leandro.techtest.data.datasource.FighterRemoteDataSource

class FighterRepository(private val dataSource: FighterRemoteDataSource) {

	suspend fun getFighters(universeName: String? = null) = dataSource.getUniverses(universeName)

}