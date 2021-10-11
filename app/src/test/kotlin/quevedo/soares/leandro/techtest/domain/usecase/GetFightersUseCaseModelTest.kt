package quevedo.soares.leandro.techtest.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import quevedo.soares.leandro.techtest.data.repository.FighterRepository
import quevedo.soares.leandro.techtest.domain.model.RequestState
import quevedo.soares.leandro.techtest.extension.second
import quevedo.soares.leandro.techtest.util.CoroutinesTestRule
import quevedo.soares.leandro.techtest.util.DataMock

@ExperimentalCoroutinesApi
class GetFightersUseCaseModelTest {

	private lateinit var getFightersUseCaseMock: GetFightersUseCase
	private lateinit var fightersRepositoryMock: FighterRepository

	@JvmField
	@Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	@get:Rule
	var coroutinesTestRule = CoroutinesTestRule()

	@Before
	fun setUp() {
		fightersRepositoryMock = mock(FighterRepository::class.java)
		getFightersUseCaseMock = GetFightersUseCase(fightersRepositoryMock)
	}

	@Test
	fun `Assures that the universes are loaded correctly`() = runBlockingTest {
		// Arrange
		val expected = DataMock.fighters
		val universeNameMock: String? = null
		val allowCacheMock = false

		whenever(fightersRepositoryMock.getFighters(universeNameMock, allowCacheMock)).thenReturn(expected)

		// Act
		val actual = getFightersUseCaseMock.invoke(universeNameMock, allowCacheMock).toList()

		// Assert
		assert(actual.first() is RequestState.Loading)
		assert(actual.second() is RequestState.Success)
		assert((actual.second() as RequestState.Success).response == expected)
	}

}