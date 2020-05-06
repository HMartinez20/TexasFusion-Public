package com.example.texasfusionpublic.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.databinding.FragmentLoginMenuBinding
import com.google.firebase.auth.FirebaseAuth

class LoginMenuFragment : Fragment() {

    companion object{
        const val FRAG_LOCATION = "location"
        const val FRAG_HOURS = "hours"
        const val FRAG_TICKETS= "tickets"
        const val FRAG_HOME= "home"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        auth.currentUser?.let {
            // Access user info
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login_menu,container,false)

        binding.truckLocation.setOnClickListener { loadFrag(FRAG_LOCATION) }
        binding.hoursOp.setOnClickListener       { loadFrag(FRAG_HOURS) }
        binding.orders.setOnClickListener        { loadFrag(FRAG_TICKETS) }
        binding.logout.setOnClickListener        {
            auth.signOut()
            Toast.makeText(this.context, "Logged out", Toast.LENGTH_SHORT).show()
            loadFrag(FRAG_HOME)
        }

        return binding.root
    }

    private fun loadFrag(frag: String){
        when(frag){
            FRAG_LOCATION ->
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_location)
            FRAG_HOURS ->
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_hours)
            FRAG_TICKETS ->
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_tickets)
            FRAG_HOME ->
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_home)
            else ->
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_home)
        }
    }

}
