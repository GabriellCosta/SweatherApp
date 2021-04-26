package dev.tigrao.sweather.di

import com.github.terrakok.cicerone.BaseRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val ciceroniModule = module {
    single(createdAtStart = true) {
        Cicerone.create()
    }

    single(createdAtStart = true) {
        get<Cicerone<Router>>().getNavigatorHolder()
    }

    single {
        get<Cicerone<Router>>().router
    }
}
