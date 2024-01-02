package com.app.zuludin.bookber.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.QuoteDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("select * from quoteentity")
    fun observeAllQuotes(): Flow<List<QuoteEntity>>

    @Query("select * from quoteentity where bookId = :bookId")
    fun loadQuotesByBook(bookId: String): LiveData<List<QuoteEntity>>

    @Query("select * from quoteentity where categoryId = :categoryId")
    suspend fun loadQuotesByCategory(categoryId: String): List<QuoteEntity>

    @Transaction
    @Query("select * from quoteentity where quoteId = :quoteId")
    suspend fun loadQuoteDetail(quoteId: String): QuoteDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuote(quote: QuoteEntity)

    @Update
    suspend fun updateQuote(quote: QuoteEntity): Int

    @Query("delete from quoteentity where quoteId = :quoteId")
    suspend fun deleteQuoteById(quoteId: String): Int

    @Transaction
    suspend fun batchUpdate(quotes: List<QuoteEntity>) {
        quotes.forEach {
            updateQuote(it)
        }
    }
}