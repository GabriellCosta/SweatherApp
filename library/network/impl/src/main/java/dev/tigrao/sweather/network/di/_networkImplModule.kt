package dev.tigrao.sweather.network.di

import dev.tigrao.sweather.network.BuildConfig
import dev.tigrao.sweather.network.NetworkBuilder
import dev.tigrao.sweather.network.http.retry.RetryCallAdapterFactory
import dev.tigrao.sweather.network.interceptor.ApiKeyInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.CallAdapter

val networkImplModule = module {

    single {
        NetworkBuilder(
            baseUrl = BuildConfig.API_URL,
        )
    }

    factory {
        HttpLoggingInterceptor()
            .also {
                if (BuildConfig.DEBUG)
                    it.level = HttpLoggingInterceptor.Level.BODY
            }
    } bind Interceptor::class

    factory {
        RetryCallAdapterFactory()
    } bind CallAdapter.Factory::class

    factory<Interceptor> { ApiKeyInterceptor() } bind Interceptor::class
}
