/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.data.api

import com.sanjay.gutenberg.data.Result
import com.sanjay.gutenberg.data.repository.remote.model.BooksResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**Interface where all the api used in app are defined.
 * @author Sanjay.Sah
 */
interface GutenbergService {

    /**
     * Api for fetching paginated Books list by category and search query
     */
    @GET("books/?mime_type=image/jpeg")
    suspend fun searchBooks(
        @Query("topic") category: String, @Query("search") query: String, @Query(
            "page"
        ) page: Int
    ): Response<BooksResponse>


}