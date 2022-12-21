package com.example.savdo.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.savdo.R
import com.example.savdo.model.CategoryModel
import kotlinx.android.synthetic.main.category_item_layout.view.*

interface CategoryAdapterCallback{
    fun onClick(item: CategoryModel)
}
class CategoryAdapter(val items: List<CategoryModel>, val callback: CategoryAdapterCallback): RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {
    class ItemHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            items.forEach {
                it.checked = false
            }

            item.checked = true
            callback.onClick(item)
            notifyDataSetChanged()
        }

        holder.itemView.TVtitle.text = item.title
        if (item.checked){
            holder.itemView.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
            holder.itemView.TVtitle.setTextColor(Color.WHITE)
        }else{
            holder.itemView.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.grey))
            holder.itemView.TVtitle.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}