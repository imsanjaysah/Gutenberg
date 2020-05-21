/*
 * ActivityComponent.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.injection

import com.sanjay.gutenberg.injection.annotations.PerActivity
import com.sanjay.gutenberg.injection.module.ActivityModule
import com.sanjay.gutenberg.ui.book_list.BooksListActivity
import com.sanjay.gutenberg.ui.category_list.CategoryListActivity
import dagger.Subcomponent

/**
 * @author Sanjay Sah
 */

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: CategoryListActivity)
    fun inject(activity: BooksListActivity)
}