package quevedo.soares.leandro.techtest.di

import com.squareup.moshi.Moshi
import org.koin.dsl.module
import quevedo.soares.leandro.techtest.converter.MoshiJsonDateConverter
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

internal val moshiModule = module {

	single {
		Moshi.Builder()
				.add(Date::class.java, MoshiJsonDateConverter())
			.build()
	}

	single { MoshiConverterFactory.create(get()) }

}