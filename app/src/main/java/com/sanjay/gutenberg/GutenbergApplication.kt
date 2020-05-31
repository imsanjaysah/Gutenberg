/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.sanjay.gutenberg.injection.AppComponent
import com.sanjay.gutenberg.injection.DaggerAppComponent
import com.sanjay.gutenberg.injection.module.ApiServiceModule
import com.sanjay.gutenberg.injection.module.AppModule
import com.sanjay.gutenberg.injection.module.RepositoryModule

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
            .build()
    }
}