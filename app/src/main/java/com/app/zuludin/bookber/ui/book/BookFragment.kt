package com.app.zuludin.bookber.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.databinding.FragmentBookBinding
import com.app.zuludin.bookber.util.ViewModelFactory
import com.app.zuludin.bookber.util.components.CategoryFilterChips

class BookFragment : Fragment() {

    private lateinit var binding: FragmentBookBinding
    private lateinit var bookAdapter: BookAdapter

    private val viewModel by viewModels<BookViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupView() {
        bookAdapter = BookAdapter()
        binding.composeFilter.setContent {
            CategoryFilterChips(categories = arrayListOf("All", "Fiction", "Non-Fiction"))
        }
    }

    private fun setupViewModel() {
        viewModel.getBooks().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {}
                    is Result.Error -> {}
                    is Result.Success -> {
                        if (result.data.isNotEmpty()) {
                            binding.emptyMessage.visibility = View.GONE
                            bookAdapter.setBookStore(result.data)
                        } else {
                            binding.emptyMessage.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerBook.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = bookAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookFragment()
    }
}