package quevedo.soares.leandro.techtest.data.datasource.local

import io.objectbox.BoxStore
import quevedo.soares.leandro.techtest.domain.model.Universe

class UniverseLocalDataSource(private val store: BoxStore) {

	private val box get() = store.boxFor(Universe::class.java)

	fun isAnyStoredLocally() = !box.isEmpty

	fun getUniverses(): List<Universe> = box.all

	fun storeUniverses(list: List<Universe>) {
		// Run in transaction
		store.runInTx {
			// Removes all stored universes
			box.removeAll()

			// Store the given list
			box.putBatched(list, list.size)
		}
	}

}