/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.data.repository.local

import com.sanjay.gutenberg.data.repository.GutenbergDataSource
import com.sanjay.gutenberg.data.repository.remote.model.Book
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Class to handle local db operations
 *
 * @author Sanjay Sah
 */
class GutenbergLocalDataSource @Inject constructor() : GutenbergDataSource {

    override fun searchBooks(page: Int, category: String, query: String?): Flowable<List<Book>> {
        TODO("Not yet implemented")
    }


}