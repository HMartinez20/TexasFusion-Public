package com.example.texasfusionpublic

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    companion object{
        const val FRAG_LOGIN = "login"
        const val FRAG_LOGIN_MENU = "login_menu"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar)
        findViewById<Button>(R.id.title).setOnClickListener {loadFragment(FRAG_LOGIN)}

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_menu, R.id.navigation_order,
                R.id.navigation_login, R.id.navigation_login_menu
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
    }

    private fun signIn(email : String, password : String){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            task ->
            if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                Log.d("MainActivity", "signInWithEmail:success")
            } else { // If sign in fails, display a message to the user.
                Log.w("MainActivity", "signInWithEmail:failure", task.exception)
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadFragment(fragment: String){
        val navController = findNavController(R.id.nav_host_fragment)
        when(fragment){
            FRAG_LOGIN -> navController.navigate(R.id.navigation_login)
            FRAG_LOGIN_MENU -> navController.navigate(R.id.navigation_login_menu)
            else -> {
                navController.navigate(R.id.navigation_home)
            }
        }
    }
}
