package com.unicorn.browser.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unicorn.browser.R

class TabsAdapter(
    private var items: MutableList<Tab>,
    private val onClick: (Int) -> Unit,
    private val onClose: (Int) -> Unit
) : RecyclerView.Adapter<TabsAdapter.TabVH>() {

    class TabVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tabTitle)
        val close: ImageButton = view.findViewById(R.id.btnCloseTab)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tab, parent, false)
        return TabVH(v)
    }

    override fun onBindViewHolder(holder: TabVH, position: Int) {
        val item = items[position]
        holder.title.text = item.url

        holder.itemView.setOnClickListener { onClick(position) }
        holder.close.setOnClickListener { onClose(position) }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Tab>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }
}