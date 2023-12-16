package com.app.zuludin.bookber.ui.quotebookmgmt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.zuludin.bookber.MainCoroutineRule
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BookCreateViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var repository: BookberRepository

    private lateinit var viewModel: QuoteBookManagementViewModel
    private val bookDummy = BookEntity(title = "Title 1", author = "Genre 1")
    private val quoteDummy = QuoteEntity(quotes = "Quote 1", author = "Author 1")

    @Before
    fun setup() {
        viewModel = QuoteBookManagementViewModel(repository)
    }

    @Test
    fun saveBook_SuccessSaveToDatabase() = runTest {
        viewModel.saveBook(bookDummy, emptyList(), "1")
        Mockito.verify(repository).saveBook(bookDummy)
    }

    @Test
    fun updateBook_SuccessUpdateToDatabase() = runTest {
        viewModel.updateBook(bookDummy)
        Mockito.verify(repository).updateBook(bookDummy)
    }

    @Test
    fun saveQuote_SuccessSaveQuoteToDatabase() = runTest {
        viewModel.saveQuote(quoteDummy)
        Mockito.verify(repository).saveQuote(quoteDummy)
    }
}