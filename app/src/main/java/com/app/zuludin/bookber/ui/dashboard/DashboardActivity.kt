package com.app.zuludin.bookber.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.databinding.ActivityDashboardBinding
import com.app.zuludin.bookber.ui.create.BookCreateActivity
import com.app.zuludin.bookber.ui.dashboard.components.CategoryFilterChips
import com.app.zuludin.bookber.util.ViewModelFactory

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        val adapter = BookAdapter()
        binding.fabAddBook.setOnClickListener {
            startActivity(Intent(applicationContext, BookCreateActivity::class.java))
        }

        binding.recyclerBook.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerBook.adapter = adapter
        binding.composeFilter.setContent {
            CategoryFilterChips(categories = arrayListOf("All", "Fiction", "Non-Fiction"))
        }

        viewModel.getBooks().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            binding.emptyMessage.visibility = View.VISIBLE
                            binding.recyclerBook.visibility = View.GONE
                        } else {
                            binding.emptyMessage.visibility = View.GONE
                            binding.recyclerBook.visibility = View.VISIBLE
                            adapter.setBookStore(result.data)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}