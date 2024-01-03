package com.app.zuludin.bookber.ui.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
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
class CategoryTypeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: BookberRepository

    private lateinit var viewModel: CategoryViewModel

    private val quoteCategory1 = CategoryEntity(id = "1", category = "Quote Category 1", type = 1)
    private val quoteCategory2 = CategoryEntity(id = "2", category = "Quote Category 2", type = 1)
    private val dummyQuoteCategories = listOf(quoteCategory1, quoteCategory2)

    private val bookCategory1 = CategoryEntity(id = "3", category = "Book Category 1", type = 2)
    private val bookCategory2 = CategoryEntity(id = "4", category = "Book Category 2", type = 2)
    private val dummyBookCategories = listOf(bookCategory1, bookCategory2)

    @Before
    fun setup() {
        viewModel = CategoryViewModel(repository)
    }

    @Test
    fun getQuotes_SuccessShowQuoteCategories() {
//        val expected = MutableLiveData<Result<List<CategoryEntity>>>()
//        expected.value = Result.Success(dummyQuoteCategories)
//        Mockito.`when`(repository.loadCategoriesByType(1)).thenReturn(expected)
//
//        val actual = viewModel.getCategories(1).getOrAwaitValue()
//
//        Mockito.verify(repository).loadCategoriesByType(1)
//        Assert.assertNotNull(actual)
//        Assert.assertTrue(actual is Result.Success)
//        Assert.assertEquals(dummyQuoteCategories.size, (actual as Result.Success).data.size)
    }

    @Test
    fun getBooks_ErrorLoadQuoteCategories() {
//        val expected = MutableLiveData<Result<List<CategoryEntity>>>()
//        expected.value = Result.Error(Exception("Error"))
//        Mockito.`when`(repository.loadCategoriesByType(1)).thenReturn(expected)
//
//        val actual = viewModel.getCategories(1).getOrAwaitValue()
//
//        Mockito.verify(repository).loadCategoriesByType(1)
//        Assert.assertNotNull(actual)
//        Assert.assertTrue(actual is Result.Error)
    }

    @Test
    fun getQuotes_SuccessShowBookCategories() {
//        val expected = MutableLiveData<Result<List<CategoryEntity>>>()
//        expected.value = Result.Success(dummyBookCategories)
//        Mockito.`when`(repository.loadCategoriesByType(2)).thenReturn(expected)
//
//        val actual = viewModel.getCategories(2).getOrAwaitValue()
//
//        Mockito.verify(repository).loadCategoriesByType(2)
//        Assert.assertNotNull(actual)
//        Assert.assertTrue(actual is Result.Success)
//        Assert.assertEquals(dummyBookCategories.size, (actual as Result.Success).data.size)
    }

    @Test
    fun getBooks_ErrorLoadBookCategories() {
//        val expected = MutableLiveData<Result<List<CategoryEntity>>>()
//        expected.value = Result.Error(Exception("Error"))
//        Mockito.`when`(repository.loadCategoriesByType(2)).thenReturn(expected)
//
//        val actual = viewModel.getCategories(2).getOrAwaitValue()
//
//        Mockito.verify(repository).loadCategoriesByType(2)
//        Assert.assertNotNull(actual)
//        Assert.assertTrue(actual is Result.Error)
    }
}