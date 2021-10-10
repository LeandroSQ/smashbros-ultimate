package quevedo.soares.leandro.techtest.data.datasource

import io.objectbox.BoxStore
import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.domain.model.Fighter_

class FighterLocalDataSource(private val store: BoxStore) {

	private val box get() = store.boxFor(Fighter::class.java)

	fun isAnyStoredLocally() = !box.isEmpty

	fun getFighters(universeName: String? = null): List<Fighter> {
		return if (universeName == null) box.all
		else box.query().equal(Fighter_.universe, universeName).build().find()
	}

	fun storeFighters(list: List<Fighter>) {
		// Run in transaction
		store.runInTx {
			// Removes all stored universes
			box.removeAll()

			// Store the given list
			box.putBatched(list, list.size)
		}
	}

}