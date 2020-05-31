package com.sanjay.gutenberg.injection.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Qualifier


@Qualifier
annotation class MainCoroutineScope

@Module
class CoroutineScopeModule {

    @Provides
    @MainCoroutineScope
    fun provideMainCoroutineScope(): CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
}