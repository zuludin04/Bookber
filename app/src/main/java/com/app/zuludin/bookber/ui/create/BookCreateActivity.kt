package com.app.zuludin.bookber.ui.create

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.app.zuludin.bookber.BookberApplication
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.databinding.ActivityBookCreateBinding
import com.app.zuludin.bookber.ui.create.components.QuoteInputField
import com.app.zuludin.bookber.ui.create.components.SaveQuoteConfirmDialog
import com.app.zuludin.bookber.ui.create.components.SelectedImageListener
import com.app.zuludin.bookber.util.ViewModelFactory

class BookCreateActivity : AppCompatActivity(), SelectedImageListener {

    private lateinit var binding: ActivityBookCreateBinding
    private val viewModel: BookCreateViewModel by viewModels {
        ViewModelFactory.getInstance(application as BookberApplication)
    }

    private var bookId: String? = null
    private var bookCoverImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Create"

        binding.quoteInputCompose.setContent {
            var showCustomDialog by remember { mutableStateOf(false) }

            QuoteInputField {
                showCustomDialog = true
            }
            if (showCustomDialog) {
                SaveQuoteConfirmDialog(
                    onDismissRequest = {
                        showCustomDialog = !showCustomDialog
                    },
                    onSaveQuote = { text ->
                        val quote = QuoteEntity(
                            quotes = "Giving absolutely everything doesn’t guarantee you get anything but it’s the only chance to get something.",
                            author = text,
                            categoryId = "1",
                            bookId = ""
                        )
                        viewModel.saveQuote(quote)
                        showCustomDialog = !showCustomDialog
                        Toast.makeText(this, "Success Save Quote", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        bookId = intent.extras?.getString("BOOK_ID")


    }

    override fun showImage(uri: Uri?) {

    }
}