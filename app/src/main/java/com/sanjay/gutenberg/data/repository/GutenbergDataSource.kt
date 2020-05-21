/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.data.repository

import com.sanjay.gutenberg.data.repository.remote.model.Book
import io.reactivex.Flowable

/**
 *
 *
 * @author Sanjay Sah
 */
interface GutenbergDataSource {
    fun searchBooks(page: Int, category: String, query: String?): Flowable<List<Book>>
}
