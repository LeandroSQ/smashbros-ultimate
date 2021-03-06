package quevedo.soares.leandro.techtest.domain.usecase

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import quevedo.soares.leandro.techtest.data.repository.UniverseRepository
import quevedo.soares.leandro.techtest.domain.model.RequestState

class GetUniversesUseCase(private val repository: UniverseRepository) {

	suspend operator fun invoke(allowCache: Boolean) = flow {
		emit(RequestState.Loading)

		val response = repository.getUniverses(allowCache)
		emit(RequestState.Success(response))
	}.catch { e ->
		e.printStackTrace()
		emit(RequestState.Error(e))
	}

}