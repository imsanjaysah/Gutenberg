/*
 * BooksResponse.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.data.repository.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Sanjay.Sah
 */
enum class BookFormat(val value: String) {
    HTML(".htm"),
    PDF(".pdf"),
    TXT(".txt")
}
data class BooksResponse(
    @SerializedName("results") val books: List<Book>
)

data class Book(
    val id: Int,
    val title: String,
    private val authors: List<Author>,
    @SerializedName("formats")
    @Expose
    private val formats: Map<String, String>,
    private var bookPath: String?
) {
    fun getCoverPhoto() = formats["image/jpeg"]

    fun getAuthor() = if(authors.isNotEmpty())  authors[0].name else ""

    fun getBookPath(orderedBookFormats: Map<BookFormat, Int>): String? {
        for (bookFormat: BookFormat in orderedBookFormats.keys) {
            bookPath = getFormat(bookFormat)
            if (bookPath != null) {
                return bookPath
            }
        }
        return null
    }


    fun getFormat(bookFormat: BookFormat): String? {
        formats.values.forEach {
            if (it.endsWith(bookFormat.value)) {
                return it
            }
        }
        return null
    }
}

data class Author(val name: String)