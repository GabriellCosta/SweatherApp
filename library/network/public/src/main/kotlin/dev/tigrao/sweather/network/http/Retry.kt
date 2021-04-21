package dev.tigrao.sweather.network.http

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Retry(val max: Int = 1)
