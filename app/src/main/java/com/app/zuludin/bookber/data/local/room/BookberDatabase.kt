package com.app.zuludin.bookber.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.util.helper.InitialDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [BookEntity::class, CategoryEntity::class, QuoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookberDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    abstract fun categoryDao(): CategoryDao

    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile
        private var INSTANCE: BookberDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context, applicationScope: CoroutineScope): BookberDatabase {
            if (INSTANCE == null) {
                synchronized(BookberDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BookberDatabase::class.java,
                        "Bookber.db"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let { database ->
                                    applicationScope.launch {
                                        val dao = database.categoryDao()
                                        dao.insertInitialCategory(InitialDataSource.getCategories())
                                    }
                                }
                            }
                        })
                        .build()
                }
            }
            return INSTANCE as BookberDatabase
        }
    }
}