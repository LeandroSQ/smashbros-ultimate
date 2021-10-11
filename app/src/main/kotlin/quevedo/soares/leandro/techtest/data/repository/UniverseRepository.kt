package quevedo.soares.leandro.techtest.data.repository

import quevedo.soares.leandro.techtest.data.datasource.local.UniverseLocalDataSource
import quevedo.soares.leandro.techtest.data.datasource.remote.UniverseRemoteDataSource
import quevedo.soares.leandro.techtest.domain.model.Universe
import quevedo.soares.leandro.techtest.helper.NetworkHelper

class UniverseRepository(private val networkHelper: NetworkHelper, private val remote: UniverseRemoteDataSource, private val local: UniverseLocalDataSource) {

	suspend fun getUniverses(allowCache: Boolean): List<Universe> {
		return if (networkHelper.isConnected() || !allowCache) {
			this.remote.getUniverses()
					.also { this.local.storeUniverses(it) }
		} else {
			this.local.getUniverses()
		}
	}

}