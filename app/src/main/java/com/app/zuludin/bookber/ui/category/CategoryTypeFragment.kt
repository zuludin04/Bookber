package com.app.zuludin.bookber.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.databinding.FragmentCategoryTypeBinding
import com.app.zuludin.bookber.util.ViewModelFactory

private const val CATEGORY_TYPE = "categoryType"

class CategoryTypeFragment : Fragment() {

    private lateinit var binding: FragmentCategoryTypeBinding
    private lateinit var categoryAdapter: CategoryTypeAdapter
    private var categoryType: String? = null

    private val viewModel by viewModels<CategoryTypeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryType = it.getString(CATEGORY_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupView() {
        categoryAdapter = CategoryTypeAdapter(categoryType!!)
        val errorMessage = if (categoryType == "Quote") "Quote is Empty" else "Book is Empty"
        binding.emptyMessage.text = errorMessage
    }

    private fun setupViewModel() {
        val type = if (categoryType == "Quote") 1 else 2
        viewModel.getCategories(type).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {}
                    is Result.Error -> {}
                    is Result.Success -> {
                        if (result.data.isNotEmpty()) {
                            binding.emptyMessage.visibility = View.GONE
                            categoryAdapter.setCategories(result.data)
                        } else {
                            binding.emptyMessage.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCategoryType.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            CategoryTypeFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_TYPE, type)
                }
            }
    }
}