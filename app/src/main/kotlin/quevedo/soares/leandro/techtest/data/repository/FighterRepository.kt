package quevedo.soares.leandro.techtest.data.repository

import quevedo.soares.leandro.techtest.data.datasource.local.FighterLocalDataSource
import quevedo.soares.leandro.techtest.data.datasource.remote.FighterRemoteDataSource
import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.helper.NetworkHelper

class FighterRepository(private val networkHelper: NetworkHelper, private val remote: FighterRemoteDataSource, private val local: FighterLocalDataSource) {

	suspend fun getFighters(universeName: String? = null, allowCache: Boolean): List<Fighter> {
		return if (networkHelper.isConnected() || !allowCache) {
			this.remote.getFighters(universeName)
					.also {
						// Only stores the list if not filtered
						if (universeName == null) this.local.storeFighters(it)
					}
		} else {
			this.local.getFighters(universeName)
		}
	}

}