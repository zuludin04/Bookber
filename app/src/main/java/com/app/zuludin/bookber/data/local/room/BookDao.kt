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

@Dao
interface BookDao {
    @Query("select * from bookentity")
    fun loadBookStore(): LiveData<List<BookEntity>>

    @Transaction
    @Query("select * from bookentity where bookId = :bookId")
    fun loadBookDetail(bookId: String): LiveData<BookDetailEntity>

    @Query("select * from bookentity where categoryId = :categoryId")
    fun loadBooksByCategory(categoryId: String): LiveData<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: BookEntity)

    @Update
    suspend fun updateBook(book: BookEntity): Int

    @Query("delete from bookentity where bookId = :bookId")
    suspend fun deleteBookById(bookId: String): Int
}