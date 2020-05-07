/**
 * OrderAdapter.kt
 *
 * Handles displaying a list of items in a recycler view.
 * Displays the user's items in their cart.
 **/
package com.example.texasfusionpublic.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.MenuRepository
import kotlinx.android.synthetic.main.order_item.view.*

class OrderAdapter(
    private val list: MutableList<MenuRepository.MenuItem>,
    val itemClick: (MenuRepository.MenuItem) -> Unit,
    val incClick: (MenuRepository.MenuItem) -> Unit,
    val decClick: (MenuRepository.MenuItem) -> Unit
) : RecyclerView.Adapter<OrderAdapter.ItemViewHolder>(){

    // Specifies the reusable layout to display the information
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false))

    // Specifies binding to display the item's information and function to call when clicked/tapped
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(list[position], itemClick, incClick, decClick)

    // Used to access each object in the list
    override fun getItemCount(): Int = list.count()

    // This class corresponds to the layout specified in the onCreateViewHolder function
    class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val name = item.itemName
        val price = item.price
        val count = item.count
        val notes = item.textNotes
        val btnInc = item.btnInc
        val btnDec = item.btnDec

        fun bind(
            menuItem: MenuRepository.MenuItem,
            itemClick: (item: MenuRepository.MenuItem) -> Unit,
            incClick: (item: MenuRepository.MenuItem) -> Unit,
            decClick: (item: MenuRepository.MenuItem) -> Unit
        ){
            itemView.setOnClickListener { itemClick(menuItem) }
            btnInc.setOnClickListener{ incClick(menuItem) }
            btnDec.setOnClickListener{ decClick(menuItem) }
            name.text = menuItem.item.itemData.name.toString() + " - " + menuItem.variations.first().itemVariationData.name.toString()
            notes.text = menuItem.modifiers?.joinToString("\n") { it.modifierData.name }
            val tempPrice = menuItem.variations.first().itemVariationData.priceMoney.amount.toString()
            price.text = "$"+ "${tempPrice.substring(0,tempPrice.length-2)}.${tempPrice.substring(tempPrice.length - 2)}"
            count.text = menuItem.count.toString()
        }
    }
}