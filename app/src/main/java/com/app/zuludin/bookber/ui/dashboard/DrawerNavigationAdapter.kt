package com.app.zuludin.bookber.ui.dashboard

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.databinding.DrawerNavItemBinding
import com.app.zuludin.bookber.domain.model.NavigationDrawer

class DrawerNavigationAdapter(
    private var items: ArrayList<NavigationDrawer>,
    private var currentPos: Int
) : RecyclerView.Adapter<DrawerNavigationAdapter.DrawerNavigationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerNavigationViewHolder {
        val binding =
            DrawerNavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrawerNavigationViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DrawerNavigationViewHolder, position: Int) {
        val context = holder.itemView.context

        if (position == currentPos) {
            holder.itemView.setBackgroundResource(R.drawable.drawer_menu_bg)
            holder.binding.navigationIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
        } else {
            holder.binding.navigationIcon.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.transparent
                )
            )
        }
        holder.binding.navigationTitle.setTextColor(Color.BLACK)

        holder.binding.navigationTitle.text = items[position].title
        holder.binding.navigationIcon.setImageResource(items[position].icon)
    }

    inner class DrawerNavigationViewHolder(val binding: DrawerNavItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}