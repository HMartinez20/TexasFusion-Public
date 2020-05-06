package com.example.texasfusionpublic.ui.order

import androidx.lifecycle.ViewModel
import com.example.texasfusionpublic.MenuRepository

class OrderViewModel : ViewModel() {

    private val repo: MenuRepository = MenuRepository.getInstance()

    fun incCount(item: MenuRepository.MenuItem){
        repo.cart.set(repo.cart.indexOf(item), item.copy(count = item.count.inc()))
    }

    fun decCount(item: MenuRepository.MenuItem){
        if(item.count == 1)
            repo.cart.remove(item)
        else
            repo.cart.set(repo.cart.indexOf(item), item.copy(count = item.count.dec()))
    }

    fun clearCart(){
        repo.cart.clear()
    }
}