/**
 * OrderFragment.kt
 *
 * Handles UI functionalities and connection to bindings and view models.
 * This fragment handles the checkout process including editing items
 * in a user's cart and adding up the total for those items.
 **/
package com.example.texasfusionpublic.ui.order

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.MenuRepository
import com.example.texasfusionpublic.databinding.FragmentOrderBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class OrderFragment : Fragment() {

    private lateinit var mViewModel: OrderViewModel
    private lateinit var binding : FragmentOrderBinding
    private lateinit var repo: MenuRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel =
            ViewModelProviders.of(this).get(OrderViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        repo = MenuRepository.getInstance()

        // Retrieves items in the user's cart
        repo.cart.let{ list ->
            val recycler: RecyclerView = binding.recycler
            recycler.apply {
                layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                adapter = OrderAdapter(list,
                    {x: MenuRepository.MenuItem -> editItem(x)},
                    {y: MenuRepository.MenuItem ->
                        mViewModel.incCount(y)
                        adapter?.notifyDataSetChanged()
                        binding.textTotalPrice.text = updateTotal(list)
                    },
                    {z: MenuRepository.MenuItem ->
                        mViewModel.decCount(z)
                        adapter?.notifyDataSetChanged()
                        binding.textTotalPrice.text = updateTotal(list)
                    })
            }
            binding.textTotalPrice.text = updateTotal(list)
        }

        binding.btnCheckout.setOnClickListener{ checkout(binding.textTotalPrice.text.toString()) }

        return binding.root
    }

    // Calculates the total according to the items in the user's cart
    private fun updateTotal(list: MutableList<MenuRepository.MenuItem>): String{
        var price: Long = 0
        list.forEach {item ->
            item.variations.forEach {variation ->
                price += (variation.itemVariationData.priceMoney.amount.times(item.count))
            }
            item.modifiers?.forEach {
                price += it.modifierData.priceMoney.amount
            }
        }
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return "$${df.format(price.div(100))}"
    }

    // Future functionality: Editing the toppings selected for the item variation
    private fun editItem(item: MenuRepository.MenuItem){}

    // Handles the button click/tap on the checkout button
    private fun checkout(total: String){
        val builder = AlertDialog.Builder(this.activity)
        builder.setTitle("Order placed!")
            .setMessage("Your total is $total.\nThank you.")
            .setPositiveButton("Okay",
                DialogInterface.OnClickListener { dialog, id ->
                    mViewModel.clearCart()
                    this.activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_home)
                })
            .setCancelable(false)
        builder.create()
        builder.show()
    }
}