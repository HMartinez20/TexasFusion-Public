package com.example.texasfusionpublic.ui.menu.itemdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.texasfusionpublic.R
import com.squareup.square.models.CatalogObject
import kotlinx.android.synthetic.main.item_variation.view.*

class VariationAdapter(
    private val variations: List<CatalogObject>,
    private val clickListener: (CatalogObject) -> Unit
): RecyclerView.Adapter<VariationAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_variation, parent, false))

    override fun getItemCount(): Int = variations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(variations[position], clickListener)

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.name
        val price = view.price
        val btn = view.btnOrder

        fun bind(item: CatalogObject, clickListener: (CatalogObject) -> Unit){
            name.text = item.itemVariationData.name

            var priceString = item.itemVariationData.priceMoney.amount.toString()
            when{
                priceString.length == 1 -> priceString = "$0.0${priceString}"
                priceString.length == 2 -> priceString = "$0.${priceString}"
                priceString.length > 2 -> priceString = "$${priceString.substring(0, priceString.length - 2)}.${priceString.substring(priceString.length-2)}"
            }
            price.text = priceString

            btn.setOnClickListener{clickListener(item)}
        }
    }
}
