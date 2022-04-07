package com.firethings.something.common.core

sealed class StatePart<out Data, out Error> {
    fun getOrNull(): Data? = when (this) {
        is Loaded -> data
        else -> null
    }

    val isLoading: Boolean
        get() = this is Loading

    val isSuccess: Boolean
        get() = this is Loaded

    val isFailure: Boolean
        get() = this is Failed

    companion object
}

object Loading : StatePart<Nothing, Nothing>()

data class Loaded<Data, Error>(val data: Data) : StatePart<Data, Error>()

data class Failed<Data, Error>(val error: Error) : StatePart<Data, Error>()

@Suppress("unused")
@Deprecated(
    message = "Deprecated in favour of Loading.",
    replaceWith = ReplaceWith("Loading", "hu.fitpuli.mpp.presentation.mvi.Loading"),
    level = DeprecationLevel.WARNING
)
fun <Data, Error> StatePart<Data, Error>.mutateLoading(): StatePart<Data, Error> = Loading

@Suppress("unused")
@Deprecated(
    message = "Deprecated in favour of Loaded.",
    replaceWith = ReplaceWith("Loaded", "hu.fitpuli.mpp.presentation.mvi.Loaded"),
    level = DeprecationLevel.WARNING
)
fun <Data, Error> StatePart<Data, Error>.mutateLoaded(data: Data): StatePart<Data, Error> = Loaded(data)

@Suppress("unused")
@Deprecated(
    message = "Deprecated in favour of Error.0",
    replaceWith = ReplaceWith("Error", "hu.fitpuli.mpp.presentation.mvi.Error"),
    level = DeprecationLevel.WARNING
)
fun <Data, Error> StatePart<Data, Error>.mutateError(error: Error): StatePart<Data, Error> = Failed(error)
