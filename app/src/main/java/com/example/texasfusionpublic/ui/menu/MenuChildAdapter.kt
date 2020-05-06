package com.example.texasfusionpublic.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.MenuRepository
import kotlinx.android.synthetic.main.menu_item.view.*

class MenuChildAdapter(
    private val items : List<MenuRepository.MenuItem>,
    val clickListener: (item: MenuRepository.MenuItem) -> Unit
) : RecyclerView.Adapter<MenuChildAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        return holder.bind(items[position], clickListener)
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val img = item.img
        val name = item.name

        fun bind(obj: MenuRepository.MenuItem, clickListener: (item: MenuRepository.MenuItem) -> Unit){
            itemView.setOnClickListener{clickListener(obj)}

            Glide.with(img).load(obj.image).circleCrop().into(img)
            name.text = obj.item.itemData.name
        }
    }
}
