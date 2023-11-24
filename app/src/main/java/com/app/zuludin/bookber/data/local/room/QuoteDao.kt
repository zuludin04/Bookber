package com.app.zuludin.bookber.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.zuludin.bookber.data.local.entity.QuoteEntity

@Dao
interface QuoteDao {
    @Query("select * from quoteentity")
    fun loadAllQuotes(): LiveData<List<QuoteEntity>>

    @Query("select * from quoteentity where bookId = :bookId")
    fun loadQuotesByBook(bookId: String): LiveData<List<QuoteEntity>>

    @Query("select * from quoteentity where categoryId = :categoryId")
    fun loadQuotesByCategory(categoryId: String): LiveData<List<QuoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuote(quote: QuoteEntity)

    @Update
    suspend fun updateQuote(quote: QuoteEntity): Int

    @Query("delete from quoteentity where quoteId = :quoteId")
    suspend fun deleteQuoteById(quoteId: String): Int
}