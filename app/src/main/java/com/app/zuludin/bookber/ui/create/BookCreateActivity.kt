package com.app.zuludin.bookber.ui.create

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.zuludin.bookber.BookberApplication
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.databinding.ActivityBookCreateBinding
import com.app.zuludin.bookber.ui.create.components.BookInformation
import com.app.zuludin.bookber.ui.create.components.QuoteInputField
import com.app.zuludin.bookber.ui.create.components.SaveQuoteConfirmDialog
import com.app.zuludin.bookber.ui.quote.QuoteAdapter
import com.app.zuludin.bookber.util.ViewModelFactory
import com.app.zuludin.bookber.util.enums.BookInfoState
import java.io.ByteArrayOutputStream

class BookCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookCreateBinding
    private val viewModel: BookCreateViewModel by viewModels {
        ViewModelFactory.getInstance(application as BookberApplication)
    }

    private var bookId: String? = null
    private var isFromQuote = BookInfoState.ADD_QUOTE

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Create"

        isFromQuote = intent.getSerializableExtra("INPUT_SOURCE") as BookInfoState
        bookId = intent.extras?.getString("BOOK_ID")

        binding.quoteInputCompose.visibility =
            if (isFromQuote == BookInfoState.ADD_BOOK) View.GONE else View.VISIBLE

        binding.bookQuoteInfoCompose.setContent {
            BookInformation(
                viewModel = viewModel,
                bookState = isFromQuote
            ) { title, author, categoryId, imageUri ->
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val bytes = stream.toByteArray()
                val bookCoverImage = Base64.encodeToString(bytes, Base64.DEFAULT)

                val book = BookEntity(
                    title = title,
                    author = author,
                    cover = bookCoverImage,
                    categoryId = categoryId
                )
                viewModel.saveBook(book)
                isFromQuote = BookInfoState.DETAIL_BOOK
                binding.bookQuoteInfoCompose.visibility = View.VISIBLE
                Toast.makeText(this, "Success Save Book", Toast.LENGTH_SHORT).show()
                binding.quoteInputCompose.visibility = View.VISIBLE
            }
        }

        binding.quoteInputCompose.setContent {
            var showCustomDialog by remember { mutableStateOf(false) }
            var quote by remember { mutableStateOf("") }

            QuoteInputField {
                quote = it
                showCustomDialog = true
            }
            if (showCustomDialog) {
                SaveQuoteConfirmDialog(
                    viewModel = viewModel,
                    quote = quote,
                    onDismissRequest = {
                        showCustomDialog = !showCustomDialog
                    },
                    onSaveQuote = { author, categoryId ->
                        val q = QuoteEntity(
                            quotes = quote,
                            author = author,
                            categoryId = categoryId,
                            bookId = bookId ?: ""
                        )
                        viewModel.saveQuote(q)
                        showCustomDialog = !showCustomDialog
                        Toast.makeText(this, "Success Save Quote", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        val quoteAdapter = QuoteAdapter()

        viewModel.getQuotes().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        quoteAdapter.setBookStore(result.data)
                    }
                }
            }
        }

        binding.rvQuoteInput.apply {
            layoutManager = LinearLayoutManager(this@BookCreateActivity)
            setHasFixedSize(true)
            adapter = quoteAdapter
        }
    }
}