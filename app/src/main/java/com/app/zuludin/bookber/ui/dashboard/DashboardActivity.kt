package com.app.zuludin.bookber.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.databinding.ActivityDashboardBinding
import com.app.zuludin.bookber.domain.model.NavigationDrawer
import com.app.zuludin.bookber.ui.book.BookFragment
import com.app.zuludin.bookber.ui.category.CategoryFragment
import com.app.zuludin.bookber.ui.create.BookCreateActivity
import com.app.zuludin.bookber.ui.quote.QuoteFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var drawerAdapter: DrawerNavigationAdapter
    private var items = arrayListOf(
        NavigationDrawer(R.drawable.ic_quote, "Quotes"),
        NavigationDrawer(R.drawable.ic_books, "Books"),
        NavigationDrawer(R.drawable.ic_category, "Category"),
        NavigationDrawer(R.drawable.ic_favorite, "Favorite"),
        NavigationDrawer(R.drawable.ic_widgets, "Widget"),
        NavigationDrawer(R.drawable.ic_settings, "Settings"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar.toolbar)
        supportActionBar?.title = ""

        binding.appBar.fabAddItems.setOnClickListener {
            startActivity(Intent(this, BookCreateActivity::class.java))
        }

        binding.navigationRv.layoutManager = LinearLayoutManager(this)
        binding.navigationRv.setHasFixedSize(true)
        binding.navigationRv.addOnItemTouchListener(
            RecyclerTouchListener(
                this,
                object : ClickListener {
                    override fun onClick(view: View, position: Int) {
                        when (position) {
                            0 -> changeSelectedFragment(QuoteFragment.newInstance())
                            1 -> changeSelectedFragment(BookFragment.newInstance())
                            2 -> changeSelectedFragment(CategoryFragment.newInstance())
                        }
                        if (position == 3 || position == 4) {
                            binding.appBar.fabAddItems.hide()
                        } else {
                            binding.appBar.fabAddItems.show()
                        }
                        updateDrawerAdapter(position)
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                })
        )

        updateDrawerAdapter(0)
        changeSelectedFragment(QuoteFragment.newInstance())
        setupDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupDrawer() {
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBar.toolbar,
            R.string.app_name,
            R.string.app_name
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateDrawerAdapter(currentPos: Int) {
        drawerAdapter = DrawerNavigationAdapter(items, currentPos)
        binding.navigationRv.adapter = drawerAdapter
        drawerAdapter.notifyDataSetChanged()
    }

    private fun changeSelectedFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_container, fragment).commit()
    }
}