package com.app.zuludin.bookber.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.zuludin.bookber.databinding.FragmentCategoryTypeBinding

private const val CATEGORY_TYPE = "categoryType"

class CategoryTypeFragment : Fragment() {

    private lateinit var binding: FragmentCategoryTypeBinding
    private var categoryType: String? = null

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
        binding.categoryType.text = categoryType
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