package com.sanjay.gutenberg.data.repository

import android.util.Log
import com.sanjay.gutenberg.data.Result
import com.sanjay.gutenberg.data.repository.remote.model.Author
import com.sanjay.gutenberg.data.repository.remote.model.Book

class FakeGutenbergRepository : GutenbergDataSource {
    override suspend fun searchBooks(
        page: Int,
        category: String,
        query: String?
    ): Result<List<Book>> {
        Log.d("Fake repository", "Calling")
        return Result.Success(
            listOf(
                Book(1, "BOOK1", listOf(Author("A1")), mapOf(), ""),
                Book(1, "BOOK2", listOf(Author("A2")), mapOf(), ""),
                Book(1, "BOOK3", listOf(Author("A3")), mapOf(), ""),
                Book(1, "BOOK4", listOf(Author("A4")), mapOf(), ""),
                Book(1, "BOOK5", listOf(Author("A5")), mapOf(), ""),
                Book(1, "BOOK6", listOf(Author("A6")), mapOf(), ""),
                Book(1, "BOOK7", listOf(Author("A7")), mapOf(), ""),
                Book(1, "BOOK8", listOf(Author("A8")), mapOf(), ""),
                Book(1, "BOOK9", listOf(Author("A9")), mapOf(), ""),
                Book(1, "BOOK10", listOf(Author("A10")), mapOf(), ""),
                Book(1, "BOOK11", listOf(Author("A11")), mapOf(), ""),
                Book(1, "BOOK12", listOf(Author("A12")), mapOf(), "")
            )
        );
    }

}