package com.app.zuludin.bookber.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.util.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sampleQuote = QuoteEntity(quotes = "Title 1")

    private lateinit var database: BookberDatabase
    private lateinit var dao: QuoteDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookberDatabase::class.java
        ).build()
        dao = database.quoteDao()
    }

    @After
    fun cleanUp() = database.close()

    @Test
    fun saveQuote_Success() = runTest {
//        dao.saveQuote(sampleQuote)
//        val actual = dao.observeAllQuotes().getOrAwaitValue()
//        Assert.assertEquals(sampleQuote.quotes, actual[0].quotes)
//        Assert.assertTrue(actual.isNotEmpty())
    }

    @Test
    fun deleteQuote_Success() = runTest {
//        dao.saveQuote(sampleQuote)
//        dao.deleteQuoteById(sampleQuote.id)
//        val actual = dao.observeAllQuotes().getOrAwaitValue()
//        Assert.assertTrue(actual.isEmpty())
    }
}