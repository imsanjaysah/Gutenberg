/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.data.repository.remote

import com.sanjay.gutenberg.data.api.GutenbergService
import com.sanjay.gutenberg.data.repository.GutenbergDataSource
import com.sanjay.gutenberg.data.repository.remote.model.Book
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Class to handle remote operations
 *
 * @author Sanjay.Sah
 */
class GutenbergRemoteDataSource @Inject constructor(private var remoteService: GutenbergService) :
    GutenbergDataSource {
    override fun searchBooks(page: Int, category: String, query: String?): Flowable<List<Book>> =
        remoteService.searchBooks(category, (query ?: ""), page).map {
            it.books
        }.toFlowable().take(1)

}