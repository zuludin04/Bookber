package com.app.zuludin.bookber.ui.category

import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.ui.category.components.CategoryItem

class CategoryTypeAdapter(private val type: String) :
    RecyclerView.Adapter<CategoryTypeAdapter.CategoryTypeViewHolder>() {
    private val categories = ArrayList<CategoryEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTypeViewHolder {
        return CategoryTypeViewHolder(ComposeView(parent.context))
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryTypeViewHolder, position: Int) {
        holder.bind(position, type)
    }

    fun setCategories(categories: List<CategoryEntity>) {
        val diffCallback = CategoryDiffCallback(this.categories, categories)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.categories.clear()
        this.categories.addAll(categories)
        diffResult.dispatchUpdatesTo(this)
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

class CategoryDiffCallback(
    private val oldData: List<CategoryEntity>,
    private val newData: List<CategoryEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition].id == newData[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition] == newData[newItemPosition]

}