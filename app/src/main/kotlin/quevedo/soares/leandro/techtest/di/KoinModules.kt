package quevedo.soares.leandro.techtest.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun setupKoin(app: Application) {
	startKoin {
		androidContext(app)
		modules(
			moshiModule,
			networkModule,
			objectBoxModule,
			dataModule,
			viewModelModule,
			coilModule
		)
	}
}