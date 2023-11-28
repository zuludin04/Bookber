package com.app.zuludin.bookber.ui.category

import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.app.zuludin.bookber.ui.category.components.CategoryItem

class CategoryTypeAdapter(private val type: String) :
    RecyclerView.Adapter<CategoryTypeAdapter.CategoryTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTypeViewHolder {
        return CategoryTypeViewHolder(ComposeView(parent.context))
    }

    override fun getItemCount(): Int = 20

    override fun onBindViewHolder(holder: CategoryTypeViewHolder, position: Int) {
        holder.bind(position, type)
    }

    inner class CategoryTypeViewHolder(private val composeView: ComposeView) :
        RecyclerView.ViewHolder(composeView.rootView) {
        fun bind(position: Int, type: String) {
            composeView.setContent {
                val color = if ((position % 2) == 0) Color.Blue else Color.Green
                CategoryItem(color = color, category = "$type $position")
            }
        }
    }
}