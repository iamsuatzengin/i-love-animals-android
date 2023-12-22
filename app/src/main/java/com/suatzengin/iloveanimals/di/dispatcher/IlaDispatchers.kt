package com.suatzengin.iloveanimals.di.dispatcher

import javax.inject.Qualifier

enum class IlaDispatchers {
    IO,
    Main,
    Default
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: IlaDispatchers)
