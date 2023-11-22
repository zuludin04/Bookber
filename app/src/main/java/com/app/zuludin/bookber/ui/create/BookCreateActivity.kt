package com.app.zuludin.bookber.ui.create

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.databinding.ActivityBookCreateBinding
import com.app.zuludin.bookber.ui.create.components.PickImageSheet
import com.app.zuludin.bookber.ui.create.components.SelectedImageListener
import com.app.zuludin.bookber.util.ViewModelFactory
import java.io.ByteArrayOutputStream

class BookCreateActivity : AppCompatActivity(), SelectedImageListener {

    private lateinit var binding: ActivityBookCreateBinding
    private val viewModel: BookCreateViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    private var bookId: String? = null
    private var bookCoverImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Create"

        bookId = intent.extras?.getString("BOOK_ID")

        if (bookId != null) {
            binding.btnCreateBook.text = "Update"
            viewModel.getDetailBook(bookId!!).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Error -> {}
                        Result.Loading -> {}
                        is Result.Success -> {
                            supportActionBar?.title = "Update"
                            binding.etBookTitle.setText(result.data.title)
                            binding.etBookGenre.setText(result.data.genre)
                        }
                    }
                }
            }
        }

        binding.ivBookImage.setOnClickListener {
            PickImageSheet().show(supportFragmentManager, "PickImageSheetTag")
        }

        binding.btnCreateBook.setOnClickListener {
            if (bookId != null) {
                viewModel.updateBook(
                    BookEntity(
                        id = bookId!!,
                        title = binding.etBookTitle.text.toString(),
                        cover = bookCoverImage,
                        genre = binding.etBookGenre.text.toString(),
                        category = "Fiction"
                    )
                )
            } else {
                viewModel.saveBook(
                    BookEntity(
                        title = binding.etBookTitle.text.toString(),
                        cover = bookCoverImage,
                        genre = binding.etBookGenre.text.toString(),
                        category = "Fiction"
                    )
                )
            }
            finish()
        }
    }

    override fun showImage(uri: Uri?) {
        if (uri != null) {
            binding.ivBookImage.setImageURI(uri)
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            bookCoverImage = Base64.encodeToString(bytes, Base64.DEFAULT)
        }
    }
}