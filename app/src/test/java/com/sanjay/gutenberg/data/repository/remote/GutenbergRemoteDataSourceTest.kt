package com.sanjay.gutenberg.data.repository.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sanjay.gutenberg.data.api.GutenbergService
import com.sanjay.gutenberg.data.repository.remote.model.Book
import com.sanjay.gutenberg.data.repository.remote.model.BooksResponse
import io.reactivex.Single
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GutenbergRemoteDataSourceTest {

    @Mock
    lateinit var apiService: GutenbergService

    private var remoteDataSource: GutenbergRemoteDataSource? = null

    private val currentPage = 1
    private val limit = 20

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        remoteDataSource = GutenbergRemoteDataSource(apiService)
    }

    @After
    fun tearDown() {
        remoteDataSource = null
    }

    @Test
    fun getBooks() {
        val booksList = emptyList<Book>()
        val booksResponse = BooksResponse(booksList)
        val observable = Single.just(booksResponse)

        whenever(apiService.searchBooks("Fiction", "", 1)).thenReturn(observable)

        remoteDataSource?.searchBooks(1, "Fiction", "")

        verify(apiService).searchBooks("Fiction", "", 1)

        assertThat(
            apiService.searchBooks("Fiction", "", 1),
            instanceOf(Single::class.java)
        )
    }
}