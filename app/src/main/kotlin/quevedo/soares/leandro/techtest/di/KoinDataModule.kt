package quevedo.soares.leandro.techtest.di

import org.koin.dsl.module
import quevedo.soares.leandro.techtest.R
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

	// region
	factory { GetFightersUseCase(get()) }
	factory { GetUniversesUseCase(get()) }
	// endregion

}