package com.app.zuludin.bookber.util

import android.content.Context
import androidx.room.Room
import com.app.zuludin.bookber.data.BookberRepositoryImpl
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.room.BookberDatabase
import com.app.zuludin.bookber.data.local.BookberLocalDataSourceImpl
import com.app.zuludin.bookber.domain.BookberRepository

object ServiceLocator {

    fun provideBookberRepository(context: Context): BookberRepository {
        return BookberRepositoryImpl(localSource = createBookLocalDataSource(context))
    }

    private fun createBookLocalDataSource(context: Context): BookberLocalDataSource {
        val database = createDatabase(context)
        return BookberLocalDataSourceImpl(database.bookDao())
    }

    private fun createDatabase(context: Context): BookberDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BookberDatabase::class.java,
            DB_NAME
        ).build()
    }
}

private const val DB_NAME = "BookBer.db"