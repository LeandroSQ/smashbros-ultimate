package quevedo.soares.leandro.techtest.helper

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import quevedo.soares.leandro.techtest.BuildConfig

private const val DATA_STORE_ID = BuildConfig.APPLICATION_ID

private val Context.dataStore by preferencesDataStore(name = DATA_STORE_ID)

class DataStoreHelper(private val context: Context) {

	// region Internal utility
	private fun <T> read(key: Preferences.Key<T>, default: T) = runBlocking {
		context.dataStore.data.map {
			it[key] ?: default
		}.first()
	}

	private fun <T> write(key: Preferences.Key<T>, value: T) = runBlocking {
		context.dataStore.edit {
			it[key] = value
		}

		Unit
	}
	// endregion

	/** Determine whether the user has already gone trough all pages of the onboarding screen **/
	var goneTroughOnBoarding: Boolean
		get() = read(booleanPreferencesKey("onboarding"), false)
		set(value) = write(booleanPreferencesKey("onboarding"), value)

}