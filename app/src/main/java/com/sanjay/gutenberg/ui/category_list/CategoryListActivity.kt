/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.ui.category_list


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanjay.gutenberg.R
import com.sanjay.gutenberg.extensions.changeToolbarFont
import com.sanjay.gutenberg.ui.BaseActivity
import com.sanjay.gutenberg.ui.book_list.BooksListActivity
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.content_category_list.*


/**
 * Activity where list of all Categories will be displayed.
 */
class CategoryListActivity : BaseActivity() {

    private lateinit var categoryListAdapter: CategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)
        toolbar.changeToolbarFont()
        activityComponent.inject(this)
        initAdapter()
    }

    /**
     * Initializing the adapter
     */
    private fun initAdapter() {
        categoryListAdapter = CategoryListAdapter(getCategoryList()) {
            //Opening books list activity. Sending category name
            BooksListActivity.start(this, it.name)
        }
        recycler_view_categories.layoutManager =
            LinearLayoutManager(this)
        //set the adapter
        recycler_view_categories.adapter = categoryListAdapter
    }

    private fun getCategoryList() = listOf(
        Category(icon = R.drawable.ic_book, name = "FICTION"),
        Category(icon = R.drawable.ic_book, name = "DRAMA"),
        Category(icon = R.drawable.ic_book, name = "HUMOR"),
        Category(icon = R.drawable.ic_book, name = "POLITICS"),
        Category(icon = R.drawable.ic_book, name = "PHILOSOPHY"),
        Category(icon = R.drawable.ic_book, name = "HISTORY"),
        Category(icon = R.drawable.ic_book, name = "ADVENTURE")
    )

}