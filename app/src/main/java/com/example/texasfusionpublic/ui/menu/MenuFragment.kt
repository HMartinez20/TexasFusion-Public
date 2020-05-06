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

    private lateinit var mViewModel: MenuViewModel
    private lateinit var repo: MenuRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_menu,container,false)
        mViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        repo = MenuRepository.getInstance()

        repo.menuItems?.let{
            val recycler: RecyclerView = root.findViewById(R.id.recyclerview)
            recycler.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                adapter = MenuAdapter(it) { item: MenuRepository.MenuItem -> onClick(item)}
            }
        }

        return root
    }

    private fun onClick(item: MenuRepository.MenuItem){
        repo.updateSelectedItem(item)
        this.activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_item)
    }
}