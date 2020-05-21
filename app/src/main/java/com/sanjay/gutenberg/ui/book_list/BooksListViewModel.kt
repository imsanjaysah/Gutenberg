/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.ui.book_list


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sanjay.gutenberg.data.repository.remote.model.Book
import com.sanjay.gutenberg.data.repository.remote.model.BookFormat
import com.sanjay.gutenberg.paging.factory.BookListPagingDataSourceFactory
import com.sanjay.gutenberg.ui.BaseViewModel
import com.sanjay.gutenberg.utils.Utility.sortBookFormat
import javax.inject.Inject

/**
 * ViewModel class to handle the business logic. Activity will be updated using LiveData events
 */
class BooksListViewModel @Inject constructor(
    private val pagingDataSourceFactory: BookListPagingDataSourceFactory
) : BaseViewModel() {

    //LiveData object for books
    var booksList: LiveData<PagedList<Book>>? = null
    lateinit var sortedBookFormats: Map<BookFormat, Int>

    //LiveData object for state
    var state = pagingDataSourceFactory.dataSource.state
    var category = pagingDataSourceFactory.dataSource.category
    var searchQuery = pagingDataSourceFactory.dataSource.searchQuery

    init {
        //Setting up Paging for fetching the books in pagination
        val config = PagedList.Config.Builder()
            .setPageSize(1)
            .setEnablePlaceholders(false)
            .build()
        booksList = Transformations.switchMap(searchQuery) {
            LivePagedListBuilder(
                pagingDataSourceFactory, config
            ).build()
        }
    }

    fun listIsEmpty(): Boolean {
        return booksList?.value?.isEmpty() ?: true
    }

    fun sortBookFormatOrder() {
        sortedBookFormats = sortBookFormat(booksList?.value!!)
    }

    //Retrying the API call
    fun retry() {
        pagingDataSourceFactory.dataSource.retry()
    }
}