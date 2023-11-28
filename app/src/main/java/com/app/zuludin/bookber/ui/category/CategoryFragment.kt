package com.app.zuludin.bookber.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.zuludin.bookber.databinding.FragmentCategoryBinding
import com.google.android.material.tabs.TabLayoutMediator

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryTabAdapter(this)
        binding.pagerTabs.adapter = adapter

        TabLayoutMediator(binding.categoryTabs, binding.pagerTabs) { tab, position ->
            tab.text = if (position == 0) "Quote" else "Book"
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoryFragment()
    }
}