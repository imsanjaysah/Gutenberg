/*
 * AppComponent.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.injection


import com.sanjay.gutenberg.injection.module.*
import dagger.Component
import javax.inject.Singleton

/**
 * @author Sanjay Sah
 */

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, RepositoryModule::class, ApiServiceModule::class, SchedulerModule::class])
interface AppComponent {

    fun activityModule(activityModule: ActivityModule): ActivityComponent


}