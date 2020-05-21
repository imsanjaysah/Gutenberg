/*
 * AppModule.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.injection.module


import android.content.Context
import com.sanjay.GutenbergApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Sanjay Sah
 */

@Module
open class AppModule(private val application: GutenbergApplication) {

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesApplication(): GutenbergApplication = application

}