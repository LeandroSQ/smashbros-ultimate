package quevedo.soares.leandro.techtest.di

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import quevedo.soares.leandro.techtest.BuildConfig
import quevedo.soares.leandro.techtest.data.service.IFighterService
import quevedo.soares.leandro.techtest.data.service.IUniverseService
import quevedo.soares.leandro.techtest.helper.NetworkHelper
import quevedo.soares.leandro.techtest.util.isInDebugMode
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal val networkModule = module {

	// OkHttp
	single {
		OkHttpClient.Builder()
				.apply {
					// Only available at Debug variants
					if (isInDebugMode()) addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
				}
				.callTimeout(10L, TimeUnit.SECONDS)
			.build()
	}

	// Retrofit
	factory {
		Retrofit.Builder()
				.baseUrl(BuildConfig.SERVER_BASE_URL)
				.client(get())
				.addConverterFactory(get<MoshiConverterFactory>())
				.build()
	}

	// region Services
	single { get<Retrofit>().create(IFighterService::class.java) }
	single { get<Retrofit>().create(IUniverseService::class.java) }
	// endregion

	// region Connectivity
	single { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
	single { NetworkHelper(get()) }
	// endregion

}
