package quevedo.soares.leandro.techtest

import android.app.Application
import quevedo.soares.leandro.techtest.di.setupKoin

class Application: Application() {

	override fun onCreate() {
		super.onCreate()

		setupKoin(this)
	}

}