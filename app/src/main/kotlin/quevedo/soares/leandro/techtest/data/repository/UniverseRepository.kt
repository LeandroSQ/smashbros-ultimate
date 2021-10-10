package quevedo.soares.leandro.techtest.data.repository

import android.content.Context
import quevedo.soares.leandro.techtest.data.datasource.UniverseLocalDataSource
import quevedo.soares.leandro.techtest.data.datasource.UniverseRemoteDataSource
import quevedo.soares.leandro.techtest.domain.model.Universe
import quevedo.soares.leandro.techtest.util.NetworkUtils

class UniverseRepository(private val context: Context, private val remote: UniverseRemoteDataSource, private val local: UniverseLocalDataSource) {

	suspend fun getUniverses(allowCache: Boolean): List<Universe> {
		return if (NetworkUtils.isConnected(context) || !allowCache) {
			this.remote.getUniverses()
					.also { this.local.storeUniverses(it) }
		} else {
			this.local.getUniverses()
		}
	}

}