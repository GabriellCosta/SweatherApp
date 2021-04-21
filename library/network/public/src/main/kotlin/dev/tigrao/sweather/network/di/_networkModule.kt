package dev.tigrao.sweather.network.di

import com.squareup.moshi.Moshi
import dev.tigrao.sweather.network.NetworkService
import dev.tigrao.sweather.network.OkhttpClientFactory
import okhttp3.Interceptor
import org.koin.dsl.module

val networkModule = module {

    single {
        Moshi.Builder().build()
    }

    single {
        NetworkService(
            networkBuilder = get(),
            okhttpClientFactory = get(),
            moshi = get(),
            callAdapterFactoryList = getAll(),
        )
            .createRetrofitInstance()
    }

    single {
        val interceptor = getAll<Interceptor>()
        OkhttpClientFactory(interceptor)
    }
}
