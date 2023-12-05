package com.app.zuludin.bookber.ui.quote

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.ui.quote.components.QuoteItem
import com.app.zuludin.bookber.ui.quote.components.SwipeableQuoteItem

class QuoteAdapter(private val isSwipeable: Boolean) :
    RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    private val quotes = ArrayList<QuoteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(ComposeView(parent.context))
    }

    override fun getItemCount(): Int = quotes.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bindView(quotes[position], isSwipeable)
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
        fun bindView(quote: QuoteEntity, isSwipeable: Boolean) {
            composeView.setContent {
                if (isSwipeable) {
                    SwipeableQuoteItem(
                        quote = quote,
                        onClick = {},
                        onRemoveFromBook = { },
                        onDeleteQuote = {}
                    )
                } else {
                    QuoteItem(quote = quote, onClick = {})
                }
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