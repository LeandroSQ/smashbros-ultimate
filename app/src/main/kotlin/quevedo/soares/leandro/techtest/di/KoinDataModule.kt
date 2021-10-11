package quevedo.soares.leandro.techtest.di

import org.koin.dsl.module
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.data.datasource.local.FighterLocalDataSource
import quevedo.soares.leandro.techtest.data.datasource.local.UniverseLocalDataSource
import quevedo.soares.leandro.techtest.data.datasource.remote.FighterRemoteDataSource
import quevedo.soares.leandro.techtest.data.datasource.remote.UniverseRemoteDataSource
import quevedo.soares.leandro.techtest.data.repository.FighterRepository
import quevedo.soares.leandro.techtest.data.repository.UniverseRepository
import quevedo.soares.leandro.techtest.domain.model.OnBoardingPage
import quevedo.soares.leandro.techtest.domain.usecase.GetFightersUseCase
import quevedo.soares.leandro.techtest.domain.usecase.GetUniversesUseCase

internal val dataModule = module {

	// region On boarding data
	factory {
		listOf(
			OnBoardingPage(image = R.drawable.ic_onboarding_page1, text = R.string.text_onboarding_page1),
			OnBoardingPage(image = R.drawable.ic_onboarding_page2, text = R.string.text_onboarding_page2),
			OnBoardingPage(image = R.drawable.ic_onboarding_page3, text = R.string.text_onboarding_page3),
		)
	}
	// endregion

	// region Data sources
	factory { FighterRemoteDataSource(get()) }
	factory { FighterLocalDataSource(get()) }
	factory { UniverseLocalDataSource(get()) }
	factory { UniverseRemoteDataSource(get()) }
	// endregion

	// region Repositories
	factory { FighterRepository(get(), get(), get()) }
	factory { UniverseRepository(get(), get(), get()) }
	// endregion

	// region Use cases
	factory { GetFightersUseCase(get()) }
	factory { GetUniversesUseCase(get()) }
	// endregion

}