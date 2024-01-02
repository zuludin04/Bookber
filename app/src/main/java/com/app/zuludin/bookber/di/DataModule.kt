package com.app.zuludin.bookber.di

import android.content.Context
import androidx.room.Room
import com.app.zuludin.bookber.data.BookberRepositoryImpl
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.BookberLocalDataSourceImpl
import com.app.zuludin.bookber.data.local.room.BookDao
import com.app.zuludin.bookber.data.local.room.BookberDatabase
import com.app.zuludin.bookber.data.local.room.CategoryDao
import com.app.zuludin.bookber.data.local.room.QuoteDao
import com.app.zuludin.bookber.domain.BookberRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository(repository: BookberRepositoryImpl): BookberRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindLocalDataSource(source: BookberLocalDataSourceImpl): BookberLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BookberDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BookberDatabase::class.java,
            "Bookber.db"
        ).build()
    }

    @Provides
    fun provideBookDao(database: BookberDatabase): BookDao = database.bookDao()

    @Provides
    fun provideQuoteDao(database: BookberDatabase): QuoteDao = database.quoteDao()

    @Provides
    fun provideCategoryDao(database: BookberDatabase): CategoryDao = database.categoryDao()
}