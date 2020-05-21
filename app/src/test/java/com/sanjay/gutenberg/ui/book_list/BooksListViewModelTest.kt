package com.sanjay.gutenberg.ui.book_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sanjay.gutenberg.data.repository.remote.model.Book
import com.sanjay.gutenberg.mockPagedList
import com.sanjay.gutenberg.paging.datasource.BookListPagingDataSource
import com.sanjay.gutenberg.paging.factory.BookListPagingDataSourceFactory
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BooksListViewModelTest {

    private var viewModel: BooksListViewModel? = null

    @Mock
    lateinit var pagingDataSourceFactory: BookListPagingDataSourceFactory

    @Mock
    lateinit var pagingDataSource: BookListPagingDataSource

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(pagingDataSourceFactory.dataSource).thenReturn(pagingDataSource)
        whenever(pagingDataSourceFactory.dataSource.state).thenReturn(
            MutableLiveData()
        )

        viewModel = BooksListViewModel(pagingDataSourceFactory)
    }

    @After
    fun close() {
        viewModel = null
    }

    @Test
    fun test_Initialization() {
        assertNotNull(viewModel?.booksList)
        assertNotNull(viewModel?.state)

    }

    @Test
    fun test_Retry() {
        viewModel?.retry()

        verify(pagingDataSourceFactory.dataSource).retry()

    }

    @Test
    fun test_ListIsEmpty() {

        assertTrue(viewModel!!.listIsEmpty())

        val books = mock<Book>()
        val pagedBooksList = MutableLiveData(mockPagedList(listOf(books)))

        viewModel?.booksList = pagedBooksList

        assertFalse(viewModel!!.listIsEmpty())

    }
}