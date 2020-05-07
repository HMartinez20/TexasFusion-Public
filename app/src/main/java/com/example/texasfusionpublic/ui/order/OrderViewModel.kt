/**
 * ViewModel.kt
 *
 * Handles business functions and calculations.
 * Handles the changes to the user's cart.
 **/
package com.example.texasfusionpublic.ui.order

import androidx.lifecycle.ViewModel
import com.example.texasfusionpublic.MenuRepository

class OrderViewModel : ViewModel() {

    private val repo: MenuRepository = MenuRepository.getInstance()

    // Increase the number of a specific item in the user's cart
    fun incCount(item: MenuRepository.MenuItem){
        repo.cart.set(repo.cart.indexOf(item), item.copy(count = item.count.inc()))
    }

    // Decrease the number of a specific item in the user's cart
    fun decCount(item: MenuRepository.MenuItem){
        if(item.count == 1)
            repo.cart.remove(item)
        else
            repo.cart.set(repo.cart.indexOf(item), item.copy(count = item.count.dec()))
    }

    // Remove all items from the user's cart
    fun clearCart(){
        repo.cart.clear()
    }
}