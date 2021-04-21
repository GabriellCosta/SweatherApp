package dev.tigrao.sweather

import android.app.Application
import dev.tigrao.sweather.di.CustomKoinApplication
import org.koin.core.context.startKoin

internal class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(koinApplication = CustomKoinApplication().create(this))
    }
}
