/**
 * CheckoutActivity.kt
 *
 * Handles UI functionalities and connection to bindings and view models.
 * This will be used in the future.
 * When user presses the checkout button on the Order screen,
 * the user will be directed to a new checkout screen to enter their
 * credit card information.
 **/
package com.example.texasfusionpublic.ui.order

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.texasfusionpublic.R
import com.google.android.gms.wallet.PaymentsClient

class CheckoutActivity : AppCompatActivity() {

    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 1
    private val handler = Handler(Looper.getMainLooper())
//    private val googlePayChargeClient: GooglePayChargeClient? = null
    private val paymentsClient: PaymentsClient? = null
//    private val orderSheet: OrderSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
    }
}
