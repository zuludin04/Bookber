package com.app.zuludin.bookber.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.util.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BookDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sampleBook = BookEntity(title = "Title 1", author = "Genre 1")

    private lateinit var database: BookberDatabase
    private lateinit var dao: BookDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookberDatabase::class.java
        ).build()
        dao = database.bookDao()
    }

    @After
    fun cleanUp() = database.close()

    @Test
    fun saveBook_Success() = runTest {
        dao.saveBook(sampleBook)
        val actual = dao.loadBookStore().getOrAwaitValue()
        Assert.assertEquals(sampleBook.title, actual[0].book.title)
        Assert.assertTrue(actual.isNotEmpty())
    }

    @Test
    fun deleteBook_Success() = runTest {
        dao.saveBook(sampleBook)
        dao.deleteBookById(sampleBook.id)
        val actual = dao.loadBookStore().getOrAwaitValue()
        Assert.assertTrue(actual.isEmpty())
    }
}