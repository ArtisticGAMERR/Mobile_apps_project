        package com.example.createacc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val itemList: List<Item>,
    private val onItemClicked: (Item) -> Unit,
    private val onButtonClicked: (Item, String) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.itemImage)
        private val textView: TextView = itemView.findViewById(R.id.itemName)
        private val likeButton: Button = itemView.findViewById(R.id.likeButton)
        private val shareButton: Button = itemView.findViewById(R.id.shareButton)

        fun bind(item: Item, onItemClicked: (Item) -> Unit, onButtonClicked: (Item, String) -> Unit) {
            imageView.setImageResource(item.imageRes)
            textView.text = item.name

            itemView.setOnClickListener {
                onItemClicked(item)
            }
            likeButton.setOnClickListener {
                onButtonClicked(item, "Liked")
            }
            shareButton.setOnClickListener {
                onButtonClicked(item, "Shared")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position], onItemClicked, onButtonClicked)
    }

    override fun getItemCount(): Int = itemList.size
}
