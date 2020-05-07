/**
 * MainActivity.kt
 *
 * Initializes the fragment to load when this activity is started.
 * Initializes the bottom navigation bar that appears at the bottom
 * of the screen.
 **/
package com.example.texasfusionpublic

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_menu, R.id.navigation_order,
                R.id.navigation_login, R.id.navigation_login_menu
            )
        )

        // Directs user to the Admin Menu page
        findViewById<Button>(R.id.title).setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_login)
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
