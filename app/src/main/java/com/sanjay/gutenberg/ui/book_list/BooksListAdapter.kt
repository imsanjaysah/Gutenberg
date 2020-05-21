/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.ui.book_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.gutenberg.R
import com.sanjay.gutenberg.constants.State
import com.sanjay.gutenberg.data.repository.remote.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_footer.view.*
import kotlinx.android.synthetic.main.list_item_book.view.*


class BooksListAdapter(private val onItemClick: (Book) -> Unit, private val retry: () -> Unit) :
    PagedListAdapter<Book, RecyclerView.ViewHolder>(diffCallback) {

    private var state = State.LOADING

    companion object {
        val DATA_VIEW_TYPE = 1
        val FOOTER_VIEW_TYPE = 2

        /**
         * DiffUtils is used improve the performance by finding difference between two lists and updating only the new items
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }
        }

    }

    private val onItemClickListener = View.OnClickListener {
        val book = it.tag as Book
        onItemClick.invoke(book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) BookViewHolder.create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as BookViewHolder).bind(getItem(position)!!, onItemClickListener)
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}

/**
 * ViewHolder to display book information
 */
class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        book: Book,
        onItemClickListener: View.OnClickListener
    ) {
        itemView.txt_title.text = book.title
        itemView.txt_author.text = book.getAuthor()
        Picasso.get().load(book.getCoverPhoto()).into(itemView.iv_cover);
        itemView.tag = book
        itemView.setOnClickListener(onItemClickListener)
    }

    companion object {
        fun create(parent: ViewGroup): BookViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_book, parent, false)
            return BookViewHolder(view)
        }
    }
}

/**
 * ViewHolder to display loader at the bottom of the list while fetching next paged data
 */
class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        itemView.progress_bar.visibility =
            if (status == State.LOADING) View.VISIBLE else View.INVISIBLE
        itemView.txt_error.visibility = if (status == State.ERROR) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.txt_error.setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}