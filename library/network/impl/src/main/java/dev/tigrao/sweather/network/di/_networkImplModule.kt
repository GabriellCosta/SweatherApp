package dev.tigrao.sweather.network.di

import br.com.hippopotamus.tabarato.network.BuildConfig
import dev.tigrao.sweather.network.http.retry.RetryCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.CallAdapter

val networkImplModule = module {

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
}
