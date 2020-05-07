/**
 * MenuAdapter.kt
 *
 * Handles displaying a list of items in a recycler view.
 * Displays the categories for the menu items.
 * Initializes another recycler view that displays the menu items for each category.
 **/
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

    // Specifies the reusable layout to display the information
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.menu_category, parent, false))

    // Specifies binding to display the item's information and function to call when clicked/tapped
    // Initializes another recycler view to display the items according to the category
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

    // Used to access each object in the list
    override fun getItemCount(): Int = list.distinctBy { it.category }.size

    // This class corresponds to the layout specified in the onCreateViewHolder function
    class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val categoryTitle = item.category
        val recycler = item.recycler

        fun bind(category: String){
            categoryTitle.text = category
        }
    }
}