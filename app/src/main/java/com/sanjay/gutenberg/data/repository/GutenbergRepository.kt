/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.data.repository

import com.sanjay.gutenberg.injection.annotations.Local
import com.sanjay.gutenberg.injection.annotations.Remote
import javax.inject.Inject

/**
 *
 * @author Sanjay Sah.
 */
open class GutenbergRepository @Inject constructor(
    @Local private val localDataSource: GutenbergDataSource,
    @Remote private val remoteDataSource: GutenbergDataSource
) :
    GutenbergDataSource {

    override fun searchBooks(page: Int, category: String, query: String?) =
        remoteDataSource.searchBooks(page, category, query)

}