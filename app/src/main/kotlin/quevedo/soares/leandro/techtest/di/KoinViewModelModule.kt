package quevedo.soares.leandro.techtest.di

import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import quevedo.soares.leandro.techtest.view.home.HomeViewModel
import quevedo.soares.leandro.techtest.view.onboarding.OnBoardingViewModel

internal val viewModelModule = module {

	single { Dispatchers.IO }

	viewModel { OnBoardingViewModel(get()) }

	viewModel { HomeViewModel(get(), get(), get()) }

}