/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.ui.book_list


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.jakewharton.rxbinding3.widget.textChanges
import com.sanjay.gutenberg.R
import com.sanjay.gutenberg.constants.State
import com.sanjay.gutenberg.extensions.changeToolbarFont
import com.sanjay.gutenberg.extensions.makeClearableEditText
import com.sanjay.gutenberg.ui.BaseActivity
import com.sanjay.gutenberg.ui.book_list.BooksListAdapter.Companion.DATA_VIEW_TYPE
import com.sanjay.gutenberg.ui.book_list.BooksListAdapter.Companion.FOOTER_VIEW_TYPE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_books_list.*
import kotlinx.android.synthetic.main.content_books_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Activity where list of all Books list will be displayed. MVVM architecture is used to separate UI and business logic
 */
class BooksListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: BooksListViewModel
    private lateinit var booksListAdapter: BooksListAdapter

    private var searchSubscription: Disposable? = null
    private var isQueryChanged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)
        setSupportActionBar(toolbar)
        toolbar.changeToolbarFont()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        activityComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BooksListViewModel::class.java)

        intent.getStringExtra(EXTRA_CATEGORY)?.let {
            viewModel.category.value = it
            viewModel.searchQuery.value = ""
            supportActionBar?.title = it
        }
        initAdapter()
        initState()
    }

    /**
     * Initializing the adapter
     */
    private fun initAdapter() {
        booksListAdapter = BooksListAdapter({
            //Opening the book in Browser
            val bookUrl = it.getBookPath(viewModel.sortedBookFormats)
            if (bookUrl != null) {
                openBook(bookUrl)
            } else {
                showErrorDialog()
            }
        }, {
            //On click of retry textview call the api again
            viewModel.retry()
        })
        recycler_view_books.layoutManager =
            GridLayoutManager(this, 3)

        (recycler_view_books.layoutManager as GridLayoutManager).spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (booksListAdapter.getItemViewType(position)) {
                        DATA_VIEW_TYPE -> 1
                        FOOTER_VIEW_TYPE -> 3 //number of columns of the grid
                        else -> -1
                    }
                }
            }
        //set the adapter
        recycler_view_books.adapter = booksListAdapter
        //Observing live data for changes, new changes are submitted to PagedAdapter
        viewModel.booksList?.observe(this, Observer {
            viewModel.sortBookFormatOrder()
            booksListAdapter.submitList(it)
            //Workaround to fix this issue
            //https://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position
            if (isQueryChanged) {
                booksListAdapter.notifyDataSetChanged()
                isQueryChanged = false
            }
        })
    }

    /**
     * Initializing the state
     */
    private fun initState() {
        et_search_books.makeClearableEditText()
        //On click of retry textview call the api again
        txt_error.setOnClickListener { viewModel.retry() }
        //Observing the different states of the API calling, and updating the UI accordingly
        viewModel.state.observe(this, Observer { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                booksListAdapter.setState(state ?: State.DONE)
            }
        })

        searchSubscription =
            et_search_books.textChanges()
                .skip(1)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    isQueryChanged = true
                    viewModel.searchQuery.value = it.toString()
                }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun openBook(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error_title)
        builder.setMessage(getString(R.string.error_can_not_open))
        builder.setPositiveButton(R.string.ok) { _, _ -> }
        builder.show()
    }



    companion object {
        private const val EXTRA_CATEGORY = "extra_category"
        fun start(activity: Activity, category: String) {
            val intent = Intent(activity, BooksListActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY, category)
            activity.startActivity(intent)
        }
    }
}