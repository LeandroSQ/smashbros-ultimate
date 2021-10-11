package quevedo.soares.leandro.techtest.view.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import quevedo.soares.leandro.techtest.data.repository.FighterRepository
import quevedo.soares.leandro.techtest.domain.enumerator.SortByPropertyEnum
import quevedo.soares.leandro.techtest.domain.model.FighterFilter
import quevedo.soares.leandro.techtest.domain.usecase.GetFightersUseCase
import quevedo.soares.leandro.techtest.domain.usecase.GetUniversesUseCase
import quevedo.soares.leandro.techtest.util.CoroutinesTestRule
import quevedo.soares.leandro.techtest.util.DataMock

@ExperimentalCoroutinesApi
class HomeViewModelTest {

	private lateinit var getUniversesUseCaseMock: GetUniversesUseCase
	private lateinit var getFightersUseCaseMock: GetFightersUseCase
	private lateinit var fightersRepositoryMock: FighterRepository
	private lateinit var viewModel: HomeViewModel
	private lateinit var dispatcher: TestCoroutineDispatcher

	@JvmField
	@Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	@get:Rule
	var coroutinesTestRule = CoroutinesTestRule()

	@Before
	fun setUp() {
		fightersRepositoryMock = mock(FighterRepository::class.java)
		getFightersUseCaseMock = GetFightersUseCase(fightersRepositoryMock)

		getUniversesUseCaseMock = mock(GetUniversesUseCase::class.java)

		dispatcher = TestCoroutineDispatcher()

		viewModel = HomeViewModel(dispatcher, getUniversesUseCaseMock, getFightersUseCaseMock)
	}

	@Test
	fun `Assures that the universes are loaded correctly`() = runBlockingTest {
		val fighters = DataMock.fighters
		val universeNameMock: String? = null
		val allowCacheMock = false

		whenever(fightersRepositoryMock.getFighters(universeNameMock, allowCacheMock)).thenReturn(fighters)

		viewModel.getFighters(allowCacheMock)
		val actual = viewModel.viewState.value


		assert(actual is HomeViewModel.ViewState.FightersLoaded)
		assert((actual as HomeViewModel.ViewState.FightersLoaded).list == fighters)
	}

	@Test
	fun `Assures the filtering by rate works`() = runBlockingTest {
		// Arrange
		val fighters = DataMock.fighters
		val rateMock = 4
		val filterMock = FighterFilter(rating = rateMock)
		val expected = fighters.filter { it.rate == rateMock }

		// Act
		viewModel.filter = filterMock
		val actual = viewModel.filterFighters(fighters)

		// Assert
		assert(actual == expected)
	}

	@Test
	fun `Assures the sorting by name works`() = runBlockingTest {
		// Arrange
		val fighters = DataMock.fighters
		val filterMock = FighterFilter(sortBy = SortByPropertyEnum.Name)
		val expected = fighters.sortedBy { it.name }

		// Act
		viewModel.filter = filterMock
		val actual = viewModel.filterFighters(fighters)

		// Assert
		assert(actual == expected)
	}

	@Test
	fun `Assures the sorting by downloads works`() = runBlockingTest {
		// Arrange
		val fighters = DataMock.fighters
		val filterMock = FighterFilter(sortBy = SortByPropertyEnum.Downloads)
		val expected = fighters.sortedByDescending { (it.downloads ?: "0").toInt() }

		// Act
		viewModel.filter = filterMock
		val actual = viewModel.filterFighters(fighters)

		// Assert
		assert(actual == expected)
	}

	@Test
	fun `Assures the sorting by rating works`() = runBlockingTest {
		// Arrange
		val fighters = DataMock.fighters
		val filterMock = FighterFilter(sortBy = SortByPropertyEnum.Rate)
		val expected = fighters.sortedByDescending { it.rate }

		// Act
		viewModel.filter = filterMock
		val actual = viewModel.filterFighters(fighters)

		// Assert
		assert(actual == expected)
	}

	@Test
	fun `Assures the sorting by price works`() = runBlockingTest {
		// Arrange
		val fighters = DataMock.fighters
		val filterMock = FighterFilter(sortBy = SortByPropertyEnum.Price)
		val expected = fighters.sortedByDescending { (it.price ?: "0").toInt() }

		// Act
		viewModel.filter = filterMock
		val actual = viewModel.filterFighters(fighters)

		// Assert
		assert(actual == expected)
	}

	@Test
	fun `Assures the sorting and filtering works`() = runBlockingTest {
		// Arrange
		val fighters = DataMock.fighters
		val rateMock = 4
		val filterMock = FighterFilter(sortBy = SortByPropertyEnum.Name, rating = rateMock)
		val expected = fighters.filter { it.rate == rateMock }.sortedBy { it.name }

		// Act
		viewModel.filter = filterMock
		val actual = viewModel.filterFighters(fighters)

		// Assert
		assert(actual == expected)
	}

}