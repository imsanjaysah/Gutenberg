/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.data.repository

import com.sanjay.gutenberg.data.Result
import com.sanjay.gutenberg.data.repository.remote.model.Book

/**
 *
 *
 * @author Sanjay Sah
 */
interface GutenbergDataSource {
    suspend fun searchBooks(page: Int, category: String, query: String?): Result<List<Book>>
}
