package quevedo.soares.leandro.techtest.domain.model

sealed class RequestState<T> {

	object Loading : RequestState<Nothing>()

	data class Success<T>(val response: T) : RequestState<T>()

	data class Error(val throwable: Throwable) : RequestState<Nothing>()

}