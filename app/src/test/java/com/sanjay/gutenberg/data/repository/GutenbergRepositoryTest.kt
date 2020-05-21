package com.sanjay.gutenberg.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sanjay.gutenberg.data.repository.local.GutenbergLocalDataSource
import com.sanjay.gutenberg.data.repository.remote.GutenbergRemoteDataSource
import com.sanjay.gutenberg.data.repository.remote.model.Book
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GutenbergRepositoryTest {

    @Mock
    lateinit var localRepository: GutenbergLocalDataSource
    @Mock
    lateinit var remoteRepository: GutenbergRemoteDataSource

    private var repository: GutenbergRepository? = null

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = GutenbergRepository(localRepository, remoteRepository)
    }

    @After
    fun tearDown() {
        repository = null
    }


    @Test
    fun getBooks() {
        val booksList = emptyList<Book>()
        val observable = Flowable.just(booksList)

        whenever(remoteRepository.searchBooks(1, "Fiction", "")).thenReturn(observable)

        repository?.searchBooks(1, "Fiction", "")

        verify(remoteRepository).searchBooks(1, "Fiction", "")

        assertThat(
            remoteRepository.searchBooks(1, "Fiction", ""),
            instanceOf(Flowable::class.java)
        )
    }
}