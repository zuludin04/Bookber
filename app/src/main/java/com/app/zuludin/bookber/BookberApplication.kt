package com.app.zuludin.bookber

import android.app.Application
import com.app.zuludin.bookber.data.BookberRepositoryImpl
import com.app.zuludin.bookber.data.local.BookberLocalDataSourceImpl
import com.app.zuludin.bookber.data.local.room.BookberDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BookberApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { BookberDatabase.getDatabase(this, applicationScope) }
    val localSource by lazy {
        BookberLocalDataSourceImpl(
            database.bookDao(),
            database.quoteDao(),
            database.categoryDao()
        )
    }
    val repository by lazy { BookberRepositoryImpl(localSource) }
}