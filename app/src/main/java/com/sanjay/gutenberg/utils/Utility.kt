/*
 * Utility.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.utils

import android.util.Log
import androidx.paging.PagedList
import com.sanjay.gutenberg.data.repository.remote.model.Book
import com.sanjay.gutenberg.data.repository.remote.model.BookFormat


/**
 * Utility class to perform some utility operations.
 *
 * @author  sanjay.sah
 */

object Utility {
    //Function to sort the Book format based on max format count
    fun sortBookFormat(books: PagedList<Book>): Map<BookFormat, Int> {
        var htmlFormatCount = 0
        var pdfFormatCount = 0
        var txtFormatCount = 0
        val bookFormatSymbols = hashMapOf<BookFormat, Int>()
        for (book in books) {
            book.getFormat(BookFormat.HTML)?.let {
                htmlFormatCount++
            }
            book.getFormat(BookFormat.PDF)?.let {
                pdfFormatCount++
            }
            book.getFormat(BookFormat.TXT)?.let {
                txtFormatCount++
            }
        }
        bookFormatSymbols[BookFormat.HTML] = htmlFormatCount
        bookFormatSymbols[BookFormat.PDF] = pdfFormatCount
        bookFormatSymbols[BookFormat.TXT] = txtFormatCount

        return bookFormatSymbols.toList().sortedBy { (_, value) -> value }.toMap()
    }
}

