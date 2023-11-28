package com.app.zuludin.bookber.ui.quote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.databinding.FragmentQuoteBinding
import com.app.zuludin.bookber.ui.dashboard.components.CategoryFilterChips
import com.app.zuludin.bookber.util.ViewModelFactory

class QuoteFragment : Fragment() {

    private lateinit var binding: FragmentQuoteBinding
    private lateinit var quoteAdapter: QuoteAdapter

    private val viewModel by viewModels<QuoteViewModel> {
        ViewModelFactory.getInstance(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupView() {
        quoteAdapter = QuoteAdapter()
        binding.composeFilter.setContent {
            CategoryFilterChips(categories = arrayListOf("All", "Fiction", "Non-Fiction"))
        }
    }

    private fun setupViewModel() {
        viewModel.loadQuotes().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {}
                    is Result.Error -> {}
                    is Result.Success -> {
                        if (result.data.isNotEmpty()) {
                            binding.emptyMessage.visibility = View.GONE
                            quoteAdapter.setBookStore(result.data)
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
            adapter = quoteAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuoteFragment()
    }
}