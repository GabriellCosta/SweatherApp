package dependencies

object PluginsVersions {
    const val kotlin = "1.4.32"
    const val androidGradlePlugin = "4.1.3"
    const val detekt = "1.1.1"
    const val googleService = "4.3.5"
    const val koin = "2.2.2"
}

object BuildPlugins {

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.kotlin}"
    const val androidGradlePlugin = "com.android.tools.build:gradle:${PluginsVersions.androidGradlePlugin}"
    const val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.detekt}"
    const val googlePlugin = "com.google.gms:google-services:${PluginsVersions.googleService}"
    const val koinPlugin = "org.koin:koin-gradle-plugin:${PluginsVersions.koin}"
}
