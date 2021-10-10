package quevedo.soares.leandro.techtest.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import quevedo.soares.leandro.techtest.BuildConfig
import quevedo.soares.leandro.techtest.data.datasource.FighterRemoteDataSource
import quevedo.soares.leandro.techtest.data.datasource.UniverseRemoteDataSource
import quevedo.soares.leandro.techtest.data.repository.FighterRepository
import quevedo.soares.leandro.techtest.data.repository.UniverseRepository
import quevedo.soares.leandro.techtest.data.service.IFighterService
import quevedo.soares.leandro.techtest.data.service.IUniverseService
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

	// region Data sources
	factory { FighterRemoteDataSource(get()) }
	factory { UniverseRemoteDataSource(get()) }
	// endregion

	// region Repositories
	factory { FighterRepository(get()) }
	factory { UniverseRepository(get()) }
	// endregion

}
