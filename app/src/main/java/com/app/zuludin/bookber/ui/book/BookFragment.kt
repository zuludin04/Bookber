package com.app.zuludin.bookber.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.zuludin.bookber.BookberApplication
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.databinding.FragmentBookBinding
import com.app.zuludin.bookber.util.ViewModelFactory
import com.app.zuludin.bookber.util.components.CategoryFilterChips

class BookFragment : Fragment() {

    private lateinit var binding: FragmentBookBinding
    private lateinit var bookAdapter: BookAdapter

    private val viewModel by viewModels<BookViewModel> {
        ViewModelFactory.getInstance(requireActivity().application as BookberApplication)
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
        binding.emptyLayout.emptyMessage.text = getString(R.string.empty_book)
        bookAdapter = BookAdapter()

    }

    private fun setupViewModel() {
        viewModel.books.observe(viewLifecycleOwner) { result ->
            if (result.isNotEmpty()) {
                binding.emptyLayout.emptyMessage.visibility = View.GONE
                bookAdapter.setBookStore(result)
            } else {
                binding.emptyLayout.emptyMessage.visibility = View.VISIBLE
            }
        }

        viewModel.categories.observe(viewLifecycleOwner) { result ->
            binding.composeFilter.setContent {
                CategoryFilterChips(categories = result)
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