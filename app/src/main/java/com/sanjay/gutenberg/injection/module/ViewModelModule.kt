/*
 * ViewModelModule.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanjay.gutenberg.injection.ViewModelFactory
import com.sanjay.gutenberg.ui.book_list.BooksListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
open class ViewModelModule {

    @Provides
    fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @IntoMap
    @ViewModelFactory.ViewModelKey(BooksListViewModel::class)
    fun provideBookListViewModel(viewModel: BooksListViewModel): ViewModel = viewModel


}