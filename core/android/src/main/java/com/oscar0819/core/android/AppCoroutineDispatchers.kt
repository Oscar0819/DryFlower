package com.oscar0819.core.android

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {
    val io: CoroutineDispatcher
    val computation: CoroutineDispatcher
    val main: CoroutineDispatcher
}

data class AppCoroutineDispatchers(
    override val io: CoroutineDispatcher,
    override val computation: CoroutineDispatcher,
    override val main: CoroutineDispatcher
): CoroutineDispatchers

object FakeAppCoroutineDispatchers : CoroutineDispatchers {
    override val io: CoroutineDispatcher = Dispatchers.Unconfined
    override val computation: CoroutineDispatcher = Dispatchers.Unconfined
    override val main: CoroutineDispatcher = Dispatchers.Unconfined
}