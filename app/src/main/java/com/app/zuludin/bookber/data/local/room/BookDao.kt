package com.app.zuludin.bookber.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Transaction
    @Query("select * from bookentity")
    fun observeAllBooks(): Flow<List<BookWithQuoteTotal>>

    @Transaction
    @Query("select * from bookentity")
    suspend fun loadBooks(): List<BookWithQuoteTotal>

    @Transaction
    @Query("select * from bookentity where bookId = :bookId")
    suspend fun loadBookDetail(bookId: String): BookDetailEntity?

    @Transaction
    @Query("select * from bookentity")
    fun loadBookWithQuoteTotal(): LiveData<List<BookWithQuoteTotal>>

    @Transaction
    @Query("select * from bookentity where categoryId = :categoryId")
    suspend fun loadBooksByCategory(categoryId: String): List<BookWithQuoteTotal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: BookEntity)

    @Update
    suspend fun updateBook(book: BookEntity): Int

    @Query("delete from bookentity where bookId = :bookId")
    suspend fun deleteBookById(bookId: String): Int
}