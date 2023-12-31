package com.app.zuludin.bookber.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.util.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: BookberRepository

    private lateinit var viewModel: DashboardViewModel
    private val book1 = BookEntity(title = "Title 1", genre = "Genre 1")
    private val book2 = BookEntity(title = "Title 2", genre = "Genre 3")
    private val book3 = BookEntity(title = "Title 2", genre = "Genre 3")
    private val dummyBooks = listOf(book1, book2, book3)

    @Before
    fun setup() {
        viewModel = DashboardViewModel(repository)
    }

    @Test
    fun getBooks_SuccessLoadBooks() {
        val expected = MutableLiveData<Result<List<BookEntity>>>()
        expected.value = Result.Success(dummyBooks)
        `when`(repository.loadBookStore()).thenReturn(expected)

        val actual = viewModel.getBooks().getOrAwaitValue()

        Mockito.verify(repository).loadBookStore()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(dummyBooks.size, (actual as Result.Success).data.size)
    }

    @Test
    fun getBooks_ErrorLoadBooks() {
        val expected = MutableLiveData<Result<List<BookEntity>>>()
        expected.value = Result.Error(Exception("Error"))
        `when`(repository.loadBookStore()).thenReturn(expected)

        val actual = viewModel.getBooks().getOrAwaitValue()

        Mockito.verify(repository).loadBookStore()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Error)
    }
}