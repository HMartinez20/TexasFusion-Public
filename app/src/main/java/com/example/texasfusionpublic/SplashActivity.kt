package com.example.texasfusionpublic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.squareup.square.Environment
import com.squareup.square.SquareClient
import com.squareup.square.api.CatalogApi

class SplashActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 2250
    private lateinit var repo: MenuRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        repo = MenuRepository.getInstance()

        val client: SquareClient = SquareClient.Builder()
            .environment(Environment.SANDBOX)
            .accessToken("YOUR TOKEN")
            .build()

        val api: CatalogApi = client.catalogApi
        api.listCatalogAsync(null, "CATEGORY,ITEM,ITEM_VARIATION,IMAGE,MODIFIER").thenAccept {
            Log.i("SplashActivity","successfully retrieved catalog")
            repo.setMenu(it.objects)
        }.exceptionally {
            Log.i("SplashActivity","Error Retrieving Catalog: $it")
            return@exceptionally null
        }

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}
