/**
 * LoginFragment.kt
 *
 * Handles UI fuctionalities and connection to bindings and view models.
 * Authenticates the user after a username and password is entered.
 **/
package com.example.texasfusionpublic.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        Log.i("LoginFragment", "User login check: ${auth.currentUser}")
        if(auth.currentUser != null)
            activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_login_menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)

        // Check that a username and password is entered.
        // Use Firebase Authentication service to authenticate user.
        binding.btnLogin.setOnClickListener{
            if(!binding.username.text.isNullOrEmpty() && !binding.password.text.isNullOrEmpty()){
                auth.signInWithEmailAndPassword(binding.username.text.toString(), binding.password.text.toString())
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_login_menu)
                        } else {
                            Toast.makeText(this.context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else
                Toast.makeText(this.context, "Please enter a username and password", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}