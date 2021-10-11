package quevedo.soares.leandro.techtest.di

import coil.ImageLoader
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import quevedo.soares.leandro.techtest.R

internal val coilModule = module {

	single {
		ImageLoader.Builder(androidContext())
				.availableMemoryPercentage(0.25)
				.crossfade(true)
				.placeholder(R.drawable.shape_logo)
				.allowHardware(false)
				.okHttpClient { // Define the caching system
					OkHttpClient.Builder().cache(CoilUtils.createDefaultCache(androidContext())).build()
				}
				.build()
	}

}