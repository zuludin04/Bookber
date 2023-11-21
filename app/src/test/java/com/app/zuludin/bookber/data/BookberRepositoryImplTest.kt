package com.app.zuludin.bookber.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.zuludin.bookber.MainCoroutineRule
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.util.getOrAwaitValue
import com.app.zuludin.bookber.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BookberRepositoryImplTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val book1 = BookEntity(title = "Title 1", genre = "Genre 1")
    private val book2 = BookEntity(title = "Title 2", genre = "Genre 3")
    private val book3 = BookEntity(title = "Title 2", genre = "Genre 3")
    private val newBook = BookEntity(title = "New Title", genre = "New Genre")
    private val localBooks = listOf(book1, book2, book3)

    private lateinit var localDataSource: BookberFakeDataSource
    private lateinit var bookberRepository: BookberRepositoryImpl

    @Before
    fun createRepository() {
        localDataSource = BookberFakeDataSource(localBooks.toMutableList())
        bookberRepository = BookberRepositoryImpl(localSource = localDataSource)
    }

    @Test
    fun getBooks_emptyRepositoryData() = runTest {
        val emptySource = BookberFakeDataSource()
        val repository = BookberRepositoryImpl(emptySource)
        val emptyBooks = repository.loadBookStore()
        emptyBooks.observeForTesting {
            Assert.assertEquals(0, (emptyBooks.value as Result.Success).data.size)
        }
    }

    @Test
    fun getBooks_successLoadFromDatabase() = runTest {
        val actual = bookberRepository.loadBookStore()
        actual.observeForTesting {
            Assert.assertNotNull(actual)
            Assert.assertEquals(localBooks, (actual.value as Result.Success).data)
            Assert.assertEquals(localBooks.size, (actual.value as Result.Success).data.size)
        }
    }

    @Test
    fun getBookDetail_successLoadFromDatabase() = runTest {
        val actual = bookberRepository.loadBookDetail(localBooks[0].id)
        actual.observeForTesting {
            Assert.assertNotNull(actual)
            Assert.assertEquals(localBooks[0], (actual.value as Result.Success).data)
        }
    }

    @Test
    fun saveBook_saveBookToDatabase() = runTest {
        localDataSource.saveBook(newBook)
        val actual = bookberRepository.loadBookStore().getOrAwaitValue()
        Assert.assertTrue((actual as Result.Success).data.contains(newBook))
    }

    @Test
    fun updateBook_updateSelectedBook() = runTest {
        localBooks[0].title = newBook.title
        localBooks[0].genre = newBook.genre

        localDataSource.updateBook(localBooks[0])

        val actual = bookberRepository.loadBookStore().getOrAwaitValue()
        val actualData = (actual as Result.Success).data[0]
        Assert.assertEquals(newBook.title, actualData.title)
        Assert.assertEquals(newBook.genre, actualData.genre)
    }

    @Test
    fun deleteBookById() = runTest {
        bookberRepository.deleteBookById(book1.id)

        val actual = bookberRepository.loadBookStore().getOrAwaitValue()
        val actualData = (actual as Result.Success).data
        Assert.assertFalse(actualData.contains(book1))
    }
}