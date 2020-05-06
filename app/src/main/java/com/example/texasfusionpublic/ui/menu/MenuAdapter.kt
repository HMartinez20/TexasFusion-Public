package com.example.texasfusionpublic.ui.menu

import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.MenuRepository
import kotlinx.android.synthetic.main.menu_category.view.*

class MenuAdapter(
    private val list: List<MenuRepository.MenuItem>,
    val clickListener: (MenuRepository.MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.menu_category, parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        val cats: MutableList<String> = mutableListOf()
        list.distinctBy { it.category }.forEach{
            cats.add(it.category)
        }
        holder.bind(cats[position])
        holder.recycler.apply{
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = MenuChildAdapter(list, clickListener)
        }
    }

    override fun getItemCount(): Int = list.distinctBy { it.category }.size

    class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val categoryTitle = item.category
        val recycler = item.recycler

        fun bind(category: String){
            categoryTitle.text = category
        }
    }
}