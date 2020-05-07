/**
 * TicketFragment.kt
 *
 * Handles UI functionalities and connection to bindings and view models.
 * This will be used in the future.
 * Accessible after successful login as an admin.
 **/
package com.example.texasfusionpublic.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.databinding.FragmentTicketsBinding

class TicketFragment : Fragment() {

    private lateinit var mViewModel: TicketViewModel
    private lateinit var binding: FragmentTicketsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel =
            ViewModelProviders.of(this).get(TicketViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tickets,container,false)

        return binding.root
    }
}