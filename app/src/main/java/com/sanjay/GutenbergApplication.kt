/*
 * Created by Sanjay.Sah
 */

package com.sanjay

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.sanjay.gutenberg.injection.AppComponent
import com.sanjay.gutenberg.injection.DaggerAppComponent
import com.sanjay.gutenberg.injection.module.ApiServiceModule
import com.sanjay.gutenberg.injection.module.AppModule
import com.sanjay.gutenberg.injection.module.RepositoryModule
import com.sanjay.gutenberg.injection.module.SchedulerModule

class GutenbergApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        buildComponent()
    }

    /**
     * Generating the Dagger Graph
     */
    private fun buildComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .repositoryModule(RepositoryModule())
            .apiServiceModule(ApiServiceModule())
            .schedulerModule(SchedulerModule())
            .build()
    }
}