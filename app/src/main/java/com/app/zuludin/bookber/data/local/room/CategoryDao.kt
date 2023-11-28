package com.app.zuludin.bookber.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.zuludin.bookber.data.local.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("select * from categoryentity where type = :type")
    fun loadAllCategories(type: Int): LiveData<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity): Int

    @Query("delete from categoryentity where categoryId = :categoryId")
    suspend fun deleteCategoryById(categoryId: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialCategory(categories: List<CategoryEntity>)
}