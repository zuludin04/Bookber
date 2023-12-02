package com.app.zuludin.bookber.ui.book

import android.content.Intent
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.ui.book.components.BookItem
import com.app.zuludin.bookber.ui.create.BookCreateActivity
import com.app.zuludin.bookber.util.enums.BookInfoState

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private val books = ArrayList<BookEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(ComposeView(parent.context))
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bindView(books[position])
    }

    fun setBookStore(books: List<BookEntity>) {
        val diffCallback = BookDiffCallback(this.books, books)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.books.clear()
        this.books.addAll(books)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class BookViewHolder(private val composeView: ComposeView) :
        RecyclerView.ViewHolder(composeView) {
        fun bindView(bookEntity: BookEntity) {
            composeView.setContent {
                BookItem(book = bookEntity, {
                    val context = composeView.context
                    val intent = Intent(context, BookCreateActivity::class.java)
                    intent.putExtra("INPUT_SOURCE", BookInfoState.DETAIL_BOOK)
                    context.startActivity(intent)
                })
            }
        }
    }
}

class BookDiffCallback(
    private val oldData: List<BookEntity>,
    private val newData: List<BookEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition].id == newData[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition] == newData[newItemPosition]

}