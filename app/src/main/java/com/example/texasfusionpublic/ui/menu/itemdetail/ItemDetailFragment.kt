/**
 * ItemDetailFragment.kt
 *
 * Handles UI fuctionalities and connection to bindings and view models.
 * Populates an item's details after a selection is made on the Menu page.
 * Handles click to an item's variation(s).
 **/
package com.example.texasfusionpublic.ui.menu.itemdetail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.MenuRepository
import com.example.texasfusionpublic.databinding.FragmentItemdetailBinding
import com.squareup.square.models.CatalogObject

class ItemDetailFragment: Fragment(){

    private lateinit var binding: FragmentItemdetailBinding
    private lateinit var repo: MenuRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_itemdetail, container, false)
        repo = MenuRepository.getInstance()

        // Retrieves the menu items from the repository
        repo.currentItem?.let {
            binding.item.text = it.item.itemData.name
            binding.description.text = it.item.itemData.description
            Glide.with(this).load(it.image).centerCrop().into(binding.image)

            val recycler: RecyclerView = binding.recyclerView
            recycler.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                adapter = VariationAdapter(it.variations){ item: CatalogObject -> chooseOptions(item) } //{ item: CatalogObject -> addToOrder(item) }
            }
        }
        return binding.root
    }

    // A popup that appears when a variation is selected.
    // Item is added to cart when "Order" button is pressed, otherwise the popup is closed.
    // If no toppings are included with the item, item is added to cart.
    private fun chooseOptions(item: CatalogObject){
        repo.currentItem?.let{ currItem ->
            if (!currItem.modifiers.isNullOrEmpty()){
                currItem.modifiers.let{ list ->
                    val mods = list.map { it.modifierData.name }.toTypedArray()
                    val selectedMods : MutableList<String> = mods.toMutableList()
                    val builder = AlertDialog.Builder(this.activity)
                    builder.setTitle(currItem.item.itemData.name)
                        .setMultiChoiceItems(
                            mods, BooleanArray(mods.size){true},
                            DialogInterface.OnMultiChoiceClickListener { _, which, isChecked ->
                                if (isChecked) {
                                    selectedMods.add(mods[which])
                                } else if (selectedMods.contains(mods[which])) {
                                    selectedMods.remove(mods[which])
                                }
                            })
                        .setPositiveButton(R.string.title_order,
                            DialogInterface.OnClickListener { _, _ ->
                                addToOrder(item, selectedMods)
                                this.activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_menu)
                            })
                        .setNegativeButton(R.string.cancel) { _, _ -> Unit }
                    builder.create()
                    builder.show()
                }
            }
            else
                addToOrder(item, listOf())
        }
    }

    // Adds the specific menu item (an item's variation) to the user's cart
    private fun addToOrder(additem: CatalogObject, mods: List<String>) {
        val match = repo.menuItems?.let { obj ->
            obj.first { it.item.id == additem.itemVariationData.itemId }
        }
        if (match != null) {
            val menuItem = MenuRepository.MenuItem(
                match.category,
                match.item,
                listOf(match.variations.first { it.id == additem.id }),
                match.image,
                match.modifiers?.filter { it.modifierData.name in mods },
                1
            )
            if (repo.cart.contains(menuItem))
                repo.cart.set(repo.cart.indexOf(menuItem), menuItem.copy(count = menuItem.count.inc()))
            else
                repo.cart.add(menuItem)
            Toast.makeText(context, "Added to Cart!", Toast.LENGTH_SHORT).show()
        }
    }
}