package quevedo.soares.leandro.techtest.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import quevedo.soares.leandro.techtest.domain.model.MyObjectBox

internal val objectBoxModule = module {

	single {
		MyObjectBox.builder()
				.androidContext(androidContext())
				.build()
	}

}