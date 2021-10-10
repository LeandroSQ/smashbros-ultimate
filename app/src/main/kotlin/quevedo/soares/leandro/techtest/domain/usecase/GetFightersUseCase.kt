package quevedo.soares.leandro.techtest.domain.usecase

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import quevedo.soares.leandro.techtest.data.repository.FighterRepository
import quevedo.soares.leandro.techtest.domain.model.RequestState

class GetFightersUseCase(private val repository: FighterRepository) {

	suspend operator fun invoke(universeName: String? = null, allowCache: Boolean) = flow {
		emit(RequestState.Loading)

		val response = repository.getFighters(universeName, allowCache)
		emit(RequestState.Success(response))
	}.catch { e ->
		e.printStackTrace()
		emit(RequestState.Error(e))
	}

}