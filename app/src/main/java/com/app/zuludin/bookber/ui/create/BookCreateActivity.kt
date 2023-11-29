package com.app.zuludin.bookber.ui.create

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.app.zuludin.bookber.BookberApplication
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
                SaveQuoteConfirmDialog {
                    showCustomDialog = !showCustomDialog
                }
            }
        }

        bookId = intent.extras?.getString("BOOK_ID")


    }

    override fun showImage(uri: Uri?) {

    }
}