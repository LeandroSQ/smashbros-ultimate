package quevedo.soares.leandro.techtest.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import quevedo.soares.leandro.techtest.domain.enumerator.SortByPropertyEnum
import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.domain.model.FighterFilter
import quevedo.soares.leandro.techtest.domain.model.RequestState
import quevedo.soares.leandro.techtest.domain.model.Universe
import quevedo.soares.leandro.techtest.domain.usecase.GetFightersUseCase
import quevedo.soares.leandro.techtest.domain.usecase.GetUniversesUseCase

class HomeViewModel(private val getUniversesUseCase: GetUniversesUseCase, private val getFightersUseCase: GetFightersUseCase) : ViewModel() {

	var filter: FighterFilter? = null

	private val _viewState = MutableStateFlow<ViewState?>(null)
	val viewState get() = this._viewState as StateFlow<ViewState?>

	fun getUniverses(allowCache: Boolean = true) {
		viewModelScope.launch(Dispatchers.IO) {
			getUniversesUseCase(allowCache).onEach {
				when (it) {
					is RequestState.Loading -> _viewState.emit(ViewState.LoadingUniverses)
					is RequestState.Success -> _viewState.emit(ViewState.UniversesLoaded(it.response))
					is RequestState.Error -> _viewState.emit(ViewState.UniversesError(it.throwable))
				}
			}.launchIn(viewModelScope)
		}
	}

	fun getFighters(fromUniverse: Universe? = null, allowCache: Boolean = true) {
		viewModelScope.launch(Dispatchers.IO) {
			getFightersUseCase(fromUniverse, allowCache).onEach {
				when (it) {
					is RequestState.Loading -> _viewState.emit(ViewState.LoadingFighters)
					is RequestState.Success -> {
						// Before emitting the value, filters it
						val filtered = filterFighters(it.response)
						_viewState.emit(ViewState.FightersLoaded(filtered))
					}
					is RequestState.Error -> _viewState.emit(ViewState.FightersError(it.throwable))
				}
			}.launchIn(viewModelScope)
		}
	}

	private fun filterFighters(list: List<Fighter>): List<Fighter> {
		// If the filter is null, just return the raw list
		if (this.filter == null) return list

		// Filters only the given rating
		return list.filter { it.rate == this.filter!!.rating }// Force wrapping in this case is allowed, because of the checking above!
				.sortedBy {
					when (filter!!.sortBy) {// Force wrapping in this case is allowed, because of the checking above!
						SortByPropertyEnum.Name -> it.name
						SortByPropertyEnum.Price -> it.price
						SortByPropertyEnum.Rate -> it.rate.toString()
						SortByPropertyEnum.Downloads -> it.downloads
					}
				}
	}

	sealed class ViewState {

		object LoadingUniverses : ViewState()
		data class UniversesError(val throwable: Throwable) : ViewState()
		data class UniversesLoaded(val list: List<Universe>) : ViewState()

		object LoadingFighters : ViewState()
		data class FightersLoaded(val list: List<Fighter>) : ViewState()
		data class FightersError(val throwable: Throwable) : ViewState()

	}

}