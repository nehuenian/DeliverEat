package com.isw.delivereat.ui.cart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.isw.delivereat.R
import com.isw.delivereat.databinding.CartActivityBinding
import com.isw.delivereat.dto.Producto
import com.isw.delivereat.ui.payment.DireccionActivity
import com.isw.delivereat.ui.payment.PaymentActivity
import com.isw.delivereat.util.EventObserver
import kotlinx.android.synthetic.main.cart_activity.*

@Suppress("KDocMissingDocumentation")
class CartActivity : AppCompatActivity() {
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cartActivityBinding: CartActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.cart_activity)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        viewModel.setUp()

        viewModel.producto.observe(this, Observer<Producto> {
            cartActivityBinding.producto = it
        })
        viewModel.onContinueBuying.observe(this, EventObserver {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("producto", viewModel.producto.value)
            startActivity(intent)
        })

        cartActivityBinding.cartViewModel = viewModel

        continue_buying_button.setOnClickListener {
            viewModel.onContinueBuyingClicked(toolbar)
        }

        plusButton.setOnClickListener {
            viewModel.onPlusProductoClicked()
        }

        minusButton.setOnClickListener {
            viewModel.onMinusProductoCliked()
        }

        supportActionBar?.title = viewModel.getActivityTitle()

    }
}
