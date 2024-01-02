package com.app.zuludin.bookber.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity

@Database(
    entities = [BookEntity::class, CategoryEntity::class, QuoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookberDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    abstract fun categoryDao(): CategoryDao

    abstract fun quoteDao(): QuoteDao
}