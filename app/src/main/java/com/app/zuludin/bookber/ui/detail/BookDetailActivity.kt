package com.app.zuludin.bookber.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.databinding.ActivityBookDetailBinding
import com.app.zuludin.bookber.ui.create.BookCreateActivity
import com.app.zuludin.bookber.util.ViewModelFactory

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding
    private val viewModel by viewModels<BookDetailViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    private var bookId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        bookId = intent.extras?.getString("BOOK_ID")

        viewModel.start(bookId)
        viewModel.book.observe(this) { result ->
            if (result != null) {
                supportActionBar?.title = result.title
                binding.detailBookTitle.text = result.title
                binding.detailBookGenre.text = result.genre
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            viewModel.book.removeObservers(this@BookDetailActivity)
            viewModel.deleteBook(bookId!!)
            finish()
        } else if (item.itemId == R.id.menu_edit) {
            val intent = Intent(applicationContext, BookCreateActivity::class.java)
            intent.putExtra("BOOK_ID", bookId)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}