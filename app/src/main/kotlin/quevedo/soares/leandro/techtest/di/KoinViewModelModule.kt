package quevedo.soares.leandro.techtest.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import quevedo.soares.leandro.techtest.view.onboarding.OnBoardingViewModel

internal val viewModelModule = module {
	viewModel { OnBoardingViewModel(get()) }
}