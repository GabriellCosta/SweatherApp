package dev.tigrao.sweather.weather.view.presentation.model

import dev.tigrao.sweather.infra.binds.NativeStringType

data class ErrorMessageVO<T>(
    val visible: Boolean,
    val nativeStringType: NativeStringType,
    val action: T?,
)
