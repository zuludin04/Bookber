package com.app.zuludin.bookber.util

import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.domain.model.Book

object DataMapper {
    fun mapBookEntityToModel(input: List<BookEntity>): List<Book> {
        val books = ArrayList<Book>()
        input.map {
            val b = Book(
                bookId = it.id,
                title = it.title,
                cover = it.cover,
                genre = it.genre,
                category = it.category
            )
            books.add(b)
        }
        return books
    }
}