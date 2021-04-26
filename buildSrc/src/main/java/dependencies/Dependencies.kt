package dependencies

import org.gradle.api.Project
import org.gradle.kotlin.dsl.closureOf

internal object Versions {

    const val okhttp = "4.9.1"
    const val retrofit = "2.9.0"
    const val supportLibrary = "1.2.0"
    const val material = "1.3.0"
    const val recyclerView = "1.1.0"
    const val jUnit4 = "4.13"
    const val androidJUnit = "1.2.0"
    const val espressoCore = "3.3.0"
    const val espressoRules = "1.3.0"
    const val roboletric = "4.3.1"
    const val timber = "4.7.1"
    const val livedata = "2.2.0"
    const val constraintLayout = "2.0.4"
    const val imageFetcher = "4.12.0"

    const val navigation = "2.3.0"

    const val mockk = "1.10.2"
}

object Dependencies {

    object Coroutines {
        object Versions {
            const val coroutines = "1.4.3"
        }

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object Moshi {
        object Versions {
            const val moshi = "1.12.0"
        }

        const val core = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val retrofit = "com.squareup.retrofit2:converter-moshi:2.9.0"
        const val coreKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

        val addLibs = closureOf<Project> {
            with(project.dependencies) {
                add("implementation", coreKotlin)
                add("kapt", codeGen)
            }
        }
    }

    object Lifecycle {
        object Versions {
            const val lifecycle = "2.2.0"
            const val lifecycleTesting = "2.1.0"
        }

        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    }

    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${PluginsVersions.kotlin}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.supportLibrary}"
    const val coreKTX = "androidx.core:core-ktx:1.3.1"
    const val fragment = "androidx.fragment:fragment-ktx:1.3.3"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val materialDesign = "com.google.android.material:material:${Versions.material}"
    const val pullToRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    object Koin {

        object Versions {
            const val koin = "2.1.6"
        }

        const val koin = "org.koin:koin-androidx-ext:${Versions.koin}"
        const val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"
        const val koinFragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
        const val koinviewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        const val kointTest = "org.koin:koin-test:${Versions.koin}"

    }

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.livedata}"
    const val lifecycle = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata}"
    const val viewModelExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.livedata}"

    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val imageFetcher = "com.github.bumptech.glide:glide:${Versions.imageFetcher}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.imageFetcher}"

    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    object Kirich {
        private const val VERSION = "1.4.1"

        const val viewBindingProperty =
            "com.kirich1409.viewbindingpropertydelegate:vbpd-noreflection:$VERSION"
    }

    object Ciceroni {
        private const val VERSION = "7.0"

        const val library = "com.github.terrakok:cicerone:$VERSION"
    }

    object Dexter {
        private const val VERSION = "6.2.2"

        const val library = "com.karumi:dexter:$VERSION"
    }
}

object TestDependencies {

    const val androidXArchCoreTest = "androidx.arch.core:core-testing:2.1.0"
    const val androidXLifecycle = "androidx.lifecycle:lifecycle-runtime:2.2.0"

    const val androidTestcore = "androidx.test:core:1.2.0"

    const val jUnit = "junit:junit:${Versions.jUnit4}"
    const val androidTestRunner = "androidx.test:runner:1.3.0"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val espressorules = "androidx.test:rules:${Versions.espressoRules}"
    const val roboletric = "org.robolectric:robolectric:${Versions.roboletric}"

    const val runner = "androidx.test:runner:1.2.0"
    const val junitExt = "androidx.test.ext:junit:1.1.2"
    const val fragmentTest = "androidx.fragment:fragment-testing:1.2.5"

    const val uiAutomator = "androidx.test.uiautomator:uiautomator:2.2.0"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"

    const val fixtures = "com.appmattus.fixture:fixture:1.1.0"
}
