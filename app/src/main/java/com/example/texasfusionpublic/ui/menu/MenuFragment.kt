/**
 * MenuFragment.kt
 *
 * Handles UI functionalities and connection to bindings and view models.
 * Populates menu items into recycler view to list them into categories.
 * Handles click/taps of menu items.
 **/
package com.example.texasfusionpublic.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.MenuRepository

class MenuFragment : Fragment() {

    private lateinit var repo: MenuRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_menu,container,false)
        repo = MenuRepository.getInstance()

        // Retrieves menu items to list in recycler view
        repo.menuItems?.let{
            val recycler: RecyclerView = root.findViewById(R.id.recyclerview)
            recycler.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                adapter = MenuAdapter(it) { item: MenuRepository.MenuItem -> onClick(item)}
            }
        }

        return root
    }

    // Handles a click/tap on a menu item
    private fun onClick(item: MenuRepository.MenuItem){
        repo.updateSelectedItem(item)
        this.activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_item)
    }
}