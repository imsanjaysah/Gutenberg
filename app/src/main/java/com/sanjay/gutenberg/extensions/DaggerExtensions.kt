/*
 * DaggerExtensions.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.extensions
import android.content.Context
import com.sanjay.GutenbergApplication

/**
 * Created by Sanjay Sah
 */

val Context.appComponent
    get() = (applicationContext as GutenbergApplication).appComponent
