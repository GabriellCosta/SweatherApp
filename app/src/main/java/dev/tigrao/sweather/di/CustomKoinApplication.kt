package dev.tigrao.sweather.di

import android.app.Application
import dev.tigrao.sweather.network.di.networkImplModule
import dev.tigrao.sweather.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication

internal class CustomKoinApplication {

    fun create(application: Application) = koinApplication {
        androidContext(application)

        modules(
            networkModule,
            networkImplModule,
        )
    }
}
