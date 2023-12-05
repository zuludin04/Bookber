package com.app.zuludin.bookber.ui.quote

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.ui.quote.components.QuoteItem

class QuoteAdapter(private val onDeleteQuote: (String) -> Unit) :
    RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    private val quotes = ArrayList<QuoteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(ComposeView(parent.context))
    }

    override fun getItemCount(): Int = quotes.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bindView(quotes[position]) { onDeleteQuote(it) }
    }

    fun setBookStore(books: List<QuoteEntity>) {
        val diffCallback = QuoteDiffCallback(this.quotes, books)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.quotes.clear()
        this.quotes.addAll(books)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class QuoteViewHolder(private val composeView: ComposeView) :
        RecyclerView.ViewHolder(composeView) {
        fun bindView(quote: QuoteEntity, onDeleteQuote: (String) -> Unit) {
            composeView.setContent {
                QuoteItem(quote = quote, onDeleteQuote = { onDeleteQuote(it) })
            }
        }
    }
}

class QuoteDiffCallback(
    private val oldData: List<QuoteEntity>,
    private val newData: List<QuoteEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition].id == newData[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition] == newData[newItemPosition]

}