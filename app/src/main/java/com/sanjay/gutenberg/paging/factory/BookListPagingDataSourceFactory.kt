package com.sanjay.gutenberg.paging.factory

import androidx.paging.DataSource
import com.sanjay.gutenberg.data.repository.remote.model.Book
import com.sanjay.gutenberg.paging.datasource.BookListPagingDataSource
import javax.inject.Inject

class BookListPagingDataSourceFactory @Inject constructor(val dataSource: BookListPagingDataSource) :
    DataSource.Factory<Int, Book>() {

    override fun create(): DataSource<Int, Book> {
        return dataSource
    }
}