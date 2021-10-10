package quevedo.soares.leandro.techtest.data.repository

import android.content.Context
import quevedo.soares.leandro.techtest.data.datasource.FighterLocalDataSource
import quevedo.soares.leandro.techtest.data.datasource.FighterRemoteDataSource
import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.util.NetworkUtils

class FighterRepository(private val context: Context, private val remote: FighterRemoteDataSource, private val local: FighterLocalDataSource) {

	suspend fun getFighters(universeName: String? = null, allowCache: Boolean): List<Fighter> {
		return if (NetworkUtils.isConnected(context) || !allowCache) {
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