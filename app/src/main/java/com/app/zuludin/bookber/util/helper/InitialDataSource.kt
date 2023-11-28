package com.app.zuludin.bookber.util.helper

import com.app.zuludin.bookber.data.local.entity.CategoryEntity

object InitialDataSource {
    fun getCategories(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(category = "Quote", type = 1),
            CategoryEntity(category = "Motivation", type = 1),
            CategoryEntity(category = "Funny", type = 1),
            CategoryEntity(category = "Romance", type = 2),
            CategoryEntity(category = "Horror", type = 2),
            CategoryEntity(category = "Fantasy", type = 2),
        )
    }
}