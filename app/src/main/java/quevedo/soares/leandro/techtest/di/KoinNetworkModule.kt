package quevedo.soares.leandro.techtest.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import quevedo.soares.leandro.techtest.BuildConfig
import quevedo.soares.leandro.techtest.extension.singleDebug
import quevedo.soares.leandro.techtest.util.isInDebugMode
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal val networkModule = module {
	// Only available at Debug variants
	singleDebug { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

	single {
		OkHttpClient.Builder()
			.apply {
				if (isInDebugMode()) addInterceptor(get<HttpLoggingInterceptor>())
			}
			.build()
	}

	factory {
		Retrofit.Builder()
			.baseUrl(BuildConfig.SERVER_BASE_URL)
			.client(get())
			.addConverterFactory(get<MoshiConverterFactory>())
			.build()
	}
}
