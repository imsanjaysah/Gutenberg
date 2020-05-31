package com.sanjay.gutenberg

import com.sanjay.gutenberg.injection.AppComponent
import com.sanjay.gutenberg.injection.DaggerAppComponent

class TestGutenbergApplication: GutenbergApplication() {
    override var appComponent: AppComponent
        get() = super.appComponent
        set(value) {
            DaggerAppComponent.builder()
        }
}
