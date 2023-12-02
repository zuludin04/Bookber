package com.app.zuludin.bookber.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.zuludin.bookber.MainCoroutineRule
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
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

    private val book1 = BookEntity(id = "1", title = "Title 1", author = "Genre 1", categoryId = "1")
    private val book2 = BookEntity(id = "2", title = "Title 2", author = "Genre 3", categoryId = "2")
    private val book3 = BookEntity(id = "3", title = "Title 2", author = "Genre 3", categoryId = "3")
    private val newBook =
        BookEntity(id = "4", title = "New Title", author = "New Genre", categoryId = "4")
    private val localBooks = listOf(book1, book2, book3)

    private val quote1 = QuoteEntity(id = "1", quotes = "Quote 1", categoryId = "1")
    private val quote2 = QuoteEntity(id = "2", quotes = "Quote 2", categoryId = "2")
    private val quote3 = QuoteEntity(id = "3", quotes = "Quote 3", categoryId = "3")
    private val newQuote = QuoteEntity(id = "4", quotes = "New Quote", categoryId = "4")
    private val localQuotes = listOf(quote1, quote2, quote3)

    private val category1 = CategoryEntity(id = "1", category = "Category 1", type = 1)
    private val category2 = CategoryEntity(id = "2", category = "Category 2", type = 1)
    private val category3 = CategoryEntity(id = "3", category = "Category 2", type = 1)
    private val newCategory = CategoryEntity(id = "4", category = "New Category", type = 1)
    private val localCategories = listOf(category1, category2, category3)

    private lateinit var localDataSource: BookberFakeDataSource
    private lateinit var bookberRepository: BookberRepositoryImpl

    @Before
    fun createRepository() {
        localDataSource = BookberFakeDataSource(
            localBooks.toMutableList(),
            localQuotes.toMutableList(),
            localCategories.toMutableList()
        )
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
    fun getBooksByCategory_successLoadFromDatabase() = runTest {
        val actual = bookberRepository.loadBooksByCategory("1")
        actual.observeForTesting {
            Assert.assertNotNull(actual)
            Assert.assertEquals(
                localBooks.filter { it.id == "1" },
                (actual.value as Result.Success).data
            )
            Assert.assertEquals(
                localBooks.filter { it.id == "1" }.size,
                (actual.value as Result.Success).data.size
            )
        }
    }

    @Test
    fun getBookDetail_successLoadFromDatabase() = runTest {
        val actual = bookberRepository.loadBookDetail(localBooks[0].id)
        actual.observeForTesting {
            Assert.assertNotNull(actual)
//            Assert.assertEquals(localBooks[0], (actual.value as Result.Success).data)
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
        localBooks[0].author = newBook.author

        localDataSource.updateBook(localBooks[0])

        val actual = bookberRepository.loadBookStore().getOrAwaitValue()
        val actualData = (actual as Result.Success).data[0]
        Assert.assertEquals(newBook.title, actualData.title)
        Assert.assertEquals(newBook.author, actualData.author)
    }

    @Test
    fun deleteBook_deleteSelectedBook() = runTest {
        localDataSource.deleteBookById(book1.id)

        val actual = bookberRepository.loadBookStore().getOrAwaitValue()
        val actualData = (actual as Result.Success).data
        Assert.assertFalse(actualData.contains(book1))
    }

    @Test
    fun getQuotes_emptyRepositoryData() = runTest {
        val emptySource = BookberFakeDataSource()
        val repository = BookberRepositoryImpl(emptySource)
        val emptyQuotes = repository.loadAllQuotes()
        emptyQuotes.observeForTesting {
            Assert.assertEquals(0, (emptyQuotes.value as Result.Success).data.size)
        }
    }

    @Test
    fun getQuotes_successLoadAllQuotesFromDatabase() = runTest {
        val actual = bookberRepository.loadAllQuotes()
        actual.observeForTesting {
            Assert.assertNotNull(actual)
            Assert.assertEquals(localQuotes, (actual.value as Result.Success).data)
            Assert.assertEquals(localQuotes.size, (actual.value as Result.Success).data.size)
        }
    }

    @Test
    fun getQuotes_successQuotesByCategoryFromDatabase() = runTest {
        val actual = bookberRepository.loadQuotesByCategory("1")
        actual.observeForTesting {
            Assert.assertNotNull(actual)
            Assert.assertEquals(
                localQuotes.filter { it.categoryId == "1" },
                (actual.value as Result.Success).data
            )
            Assert.assertEquals(
                localQuotes.filter { it.categoryId == "1" }.size,
                (actual.value as Result.Success).data.size
            )
        }
    }

    @Test
    fun getQuotes_successQuotesByBookFromDatabase() = runTest {
        val actual = bookberRepository.loadQuotesByBook("1")
        actual.observeForTesting {
            Assert.assertNotNull(actual)
            Assert.assertEquals(
                localQuotes.filter { it.bookId == "1" },
                (actual.value as Result.Success).data
            )
            Assert.assertEquals(
                localQuotes.filter { it.bookId == "1" }.size,
                (actual.value as Result.Success).data.size
            )
        }
    }

    @Test
    fun saveQuote_saveQuoteToDatabase() = runTest {
        localDataSource.saveQuote(newQuote)
        val actual = bookberRepository.loadAllQuotes().getOrAwaitValue()
        Assert.assertTrue((actual as Result.Success).data.contains(newQuote))
    }

    @Test
    fun updateQuote_updateSelectedQuote() = runTest {
        localQuotes[0].quotes = newQuote.quotes

        localDataSource.updateQuote(localQuotes[0])

        val actual = bookberRepository.loadAllQuotes().getOrAwaitValue()
        val actualData = (actual as Result.Success).data[0]
        Assert.assertEquals(newQuote.quotes, actualData.quotes)
    }

    @Test
    fun deleteQuote_deleteSelectedQuote() = runTest {
        localDataSource.deleteQuoteById(quote1.id)

        val actual = bookberRepository.loadAllQuotes().getOrAwaitValue()
        val actualData = (actual as Result.Success).data
        Assert.assertFalse(actualData.contains(quote1))
    }

    @Test
    fun getCategories_successLoadCategoriesFromDatabase() = runTest {
        val actual = bookberRepository.loadCategoriesByType(1)
        actual.observeForTesting {
            Assert.assertNotNull(actual)
            Assert.assertEquals(
                localCategories.filter { it.type == 1 },
                (actual.value as Result.Success).data
            )
            Assert.assertEquals(
                localCategories.filter { it.type == 1 }.size,
                (actual.value as Result.Success).data.size
            )
        }
    }

    @Test
    fun saveCategory_saveCategoryToDatabase() = runTest {
        localDataSource.saveCategory(newCategory)
        val actual = bookberRepository.loadCategoriesByType(1).getOrAwaitValue()
        Assert.assertTrue((actual as Result.Success).data.contains(newCategory))
    }

    @Test
    fun updateCategory_updateSelectedCategory() = runTest {
        localCategories[0].category = newCategory.category

        localDataSource.updateCategory(localCategories[0])

        val actual = bookberRepository.loadCategoriesByType(1).getOrAwaitValue()
        val actualData = (actual as Result.Success).data[0]
        Assert.assertEquals(newCategory.category, actualData.category)
    }

    @Test
    fun deleteCategory_deleteSelectedCategory() = runTest {
        localDataSource.deleteCategoryById(category1.id)

        val actual = bookberRepository.loadCategoriesByType(1).getOrAwaitValue()
        val actualData = (actual as Result.Success).data
        Assert.assertFalse(actualData.contains(category1))
    }
}