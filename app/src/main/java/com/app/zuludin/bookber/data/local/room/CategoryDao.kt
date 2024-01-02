package com.app.zuludin.bookber.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("select * from categoryentity where type = :type")
    fun observeCategoryByType(type: Int): Flow<List<CategoryEntity>>

    @Query("select * from categoryentity where type = :type")
    suspend fun loadCategories(type: Int): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(category: CategoryEntity)

    @Query("delete from categoryentity where categoryId = :categoryId")
    suspend fun deleteCategoryById(categoryId: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialCategory(categories: List<CategoryEntity>)
}