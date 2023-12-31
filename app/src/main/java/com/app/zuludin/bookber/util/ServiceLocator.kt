package com.app.zuludin.bookber.util

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.zuludin.bookber.data.BookberRepositoryImpl
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.BookberLocalDataSourceImpl
import com.app.zuludin.bookber.data.local.room.BookberDatabase
import com.app.zuludin.bookber.domain.BookberRepository

object ServiceLocator {

    fun provideBookberRepository(context: Context): BookberRepository {
        return BookberRepositoryImpl(localSource = createBookLocalDataSource(context))
    }

    private fun createBookLocalDataSource(context: Context): BookberLocalDataSource {
        val database = createDatabase(context)
        return BookberLocalDataSourceImpl(
            database.bookDao(),
            database.quoteDao(),
            database.categoryDao()
        )
    }

    private fun createDatabase(context: Context): BookberDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BookberDatabase::class.java,
            DB_NAME
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                }
            })
            .build()
    }
}

private const val DB_NAME = "BookBer.db"