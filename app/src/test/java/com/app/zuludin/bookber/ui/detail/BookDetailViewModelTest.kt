package com.app.zuludin.bookber.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.util.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: BookberRepository

    private lateinit var viewModel: BookDetailViewModel
    private val book1 = BookEntity(title = "Title 1", genre = "Genre 1")

    @Before
    fun setup() {
        viewModel = BookDetailViewModel(repository)
    }

    @Test
    fun getDetailBook_successLoadBookById() {
        val expected = MutableLiveData<Result<BookEntity>>()
        expected.value = Result.Success(book1)
        Mockito.`when`(repository.loadBookDetail(book1.id)).thenReturn(expected)

        val actual = viewModel.getDetailBook(book1.id).getOrAwaitValue()

        Mockito.verify(repository).loadBookDetail(book1.id)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(book1, (actual as Result.Success).data)
    }

    @Test
    fun deleteBook_SuccessDeleteToDatabase() = runTest {
        viewModel.deleteBook(book1.id)
        Mockito.verify(repository).deleteBookById(book1.id)
    }
}