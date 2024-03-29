package com.example.savdo.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.savdo.R
import com.example.savdo.model.ProductModel
import com.example.savdo.screen.productdetail.ProductDetailActivity
import com.example.savdo.utils.Constants
import kotlinx.android.synthetic.main.product_item_layout.view.*

class ProductAdapter(val items: List<ProductModel>):RecyclerView.Adapter<ProductAdapter.ItemHolder>() {
    class ItemHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, item)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.title.text = item.name
        holder.itemView.comment.text = item.name
        holder.itemView.price.text = item.price
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAAGE+item.image).into(holder.itemView.imgProduct)

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}