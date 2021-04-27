package dev.tigrao.sweather.infra.binds

import androidx.annotation.StringRes

data class NativeStringType(
    val raw: String? = null,
    @StringRes val res: Int? = null,
)
