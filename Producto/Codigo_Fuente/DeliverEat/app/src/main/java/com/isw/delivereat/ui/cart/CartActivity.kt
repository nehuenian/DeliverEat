package com.isw.delivereat.ui.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.isw.delivereat.R
import kotlinx.android.synthetic.main.cart_activity.*

@Suppress("KDocMissingDocumentation")
class CartActivity : AppCompatActivity() {
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.cart_activity)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        supportActionBar?.title = viewModel.getActivityTitle()

        if (savedInstanceState == null) {
            val fragment = CartFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.cart_container, fragment)
                .commit()
        }

    }
}
