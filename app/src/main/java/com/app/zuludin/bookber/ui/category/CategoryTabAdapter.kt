package com.app.zuludin.bookber.ui.category

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoryTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            CategoryTypeFragment.newInstance("Quote")
        } else {
            CategoryTypeFragment.newInstance("Book")
        }
    }
}