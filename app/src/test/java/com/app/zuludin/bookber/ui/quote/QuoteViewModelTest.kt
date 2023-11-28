package com.app.zuludin.bookber.ui.quote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.util.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class QuoteViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: BookberRepository

    private lateinit var viewModel: QuoteViewModel
    private val quote1 = QuoteEntity(id = "1", quotes = "Quote 1", categoryId = "1")
    private val quote2 = QuoteEntity(id = "2", quotes = "Quote 2", categoryId = "2")
    private val quote3 = QuoteEntity(id = "3", quotes = "Quote 3", categoryId = "3")
    private val dummyQuotes = listOf(quote1, quote2, quote3)

    @Before
    fun setup() {
        viewModel = QuoteViewModel(repository)
    }

    @Test
    fun getQuotes_SuccessLoadQuotesFromRepository() {
        val expected = MutableLiveData<Result<List<QuoteEntity>>>()
        expected.value = Result.Success(dummyQuotes)
        Mockito.`when`(repository.loadAllQuotes()).thenReturn(expected)

        val actual = viewModel.loadQuotes().getOrAwaitValue()

        Mockito.verify(repository).loadAllQuotes()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(dummyQuotes.size, (actual as Result.Success).data.size)
    }

    @Test
    fun getQuotes_SuccessLoadQuotesButEmpty() {
        val expected = MutableLiveData<Result<List<QuoteEntity>>>()
        expected.value = Result.Success(emptyList())
        Mockito.`when`(repository.loadAllQuotes()).thenReturn(expected)

        val actual = viewModel.loadQuotes().getOrAwaitValue()

        Mockito.verify(repository).loadAllQuotes()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(0, (actual as Result.Success).data.size)
    }

    @Test
    fun getQuotes_FailedLoadQuotesFromRepository() {
        val expected = MutableLiveData<Result<List<QuoteEntity>>>()
        expected.value = Result.Error(Exception("Error"))
        Mockito.`when`(repository.loadAllQuotes()).thenReturn(expected)

        val actual = viewModel.loadQuotes().getOrAwaitValue()

        Mockito.verify(repository).loadAllQuotes()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Error)
    }
}