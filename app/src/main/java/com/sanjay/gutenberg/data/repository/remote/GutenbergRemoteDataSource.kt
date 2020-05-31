/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.data.repository.remote

import com.sanjay.gutenberg.data.Result
import com.sanjay.gutenberg.data.api.GutenbergService
import com.sanjay.gutenberg.data.repository.GutenbergDataSource
import com.sanjay.gutenberg.data.repository.remote.model.Book
import javax.inject.Inject

/**
 * Class to handle remote operations
 *
 * @author Sanjay.Sah
 */
class GutenbergRemoteDataSource @Inject constructor(private var remoteService: GutenbergService) :
    GutenbergDataSource {
    override suspend fun searchBooks(
        page: Int,
        category: String,
        query: String?
    ): Result<List<Book>> {
        val apiResponse = remoteService.searchBooks(category, (query ?: ""), page)

        if (apiResponse.isSuccessful && apiResponse.body() != null) {
            return Result.Success(apiResponse.body()!!.books)
        }
        return Result.Error(Exception(apiResponse.message()))
    }


}