package com.app.zuludin.bookber.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.zuludin.bookber.data.local.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class BookberDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}