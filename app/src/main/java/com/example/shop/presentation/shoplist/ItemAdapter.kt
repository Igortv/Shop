package com.example.shop.presentation.shoplist

import com.example.shop.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.domain.model.Item
import com.squareup.picasso.Picasso


class ItemAdapter(private val list: List<Item>, private val listItemCLickListener: OnListItemClickListener) :
    RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Item = list[position]
        holder.bind(item, listItemCLickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

 class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mItem: Item? = null

     val itemName: TextView = itemView.findViewById(R.id.listItemName)
     val itemPrice: TextView = itemView.findViewById(R.id.listItemPrice)
     val imageView: ImageView = itemView.findViewById(R.id.listItemImage)

    fun bind(item: Item, listItemClickListener: OnListItemClickListener) {
        mItem = item
        itemName.setText(item.name)
        itemPrice.setText(item.price)
        Picasso.get()
            .load(item.imageUrl)
            .placeholder(R.drawable.placeholder) //.error(R.drawable.loading_image_error)
            .into(imageView)
        itemView.setOnClickListener {
            listItemClickListener.onItemClicked(item.id)
        }
    }
}

interface OnListItemClickListener {
    fun onItemClicked(itemId: String)
}