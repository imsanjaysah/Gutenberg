/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.ui.category_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.gutenberg.R
import kotlinx.android.synthetic.main.category_list_item.view.*

class CategoryListAdapter(
    private val categories: List<Category>,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val onItemClickListener = View.OnClickListener {
        val category = it.tag as Category
        onItemClick.invoke(category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(categories[position], onItemClickListener)
    }

    override fun getItemCount() = categories.size
}

/**
 * ViewHolder to display category information
 */
class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        category: Category,
        onItemClickListener: View.OnClickListener
    ) {
        itemView.txt_name.text = category.name
        itemView.iv_icon.setImageResource(category.icon)

        itemView.tag = category
        itemView.setOnClickListener(onItemClickListener)
    }

    companion object {
        fun create(parent: ViewGroup): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_list_item, parent, false)
            return CategoryViewHolder(view)
        }
    }
}