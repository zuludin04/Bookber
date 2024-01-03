package com.app.zuludin.bookber.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.util.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CategoryDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sampleCategory = CategoryEntity(category = "Title 1", type = 1)

    private lateinit var database: BookberDatabase
    private lateinit var dao: CategoryDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookberDatabase::class.java
        ).build()
        dao = database.categoryDao()
    }

    @After
    fun cleanUp() = database.close()

    @Test
    fun saveCategory_Success() = runTest {
//        dao.saveCategory(sampleCategory)
//        val actual = dao.loadAllCategories(1).getOrAwaitValue()
//        Assert.assertEquals(sampleCategory.category, actual[0].category)
//        Assert.assertTrue(actual.isNotEmpty())
    }

    @Test
    fun deleteCategory_Success() = runTest {
//        dao.saveCategory(sampleCategory)
//        dao.deleteCategoryById(sampleCategory.id)
//        val actual = dao.loadAllCategories(1).getOrAwaitValue()
//        Assert.assertTrue(actual.isEmpty())
    }
}