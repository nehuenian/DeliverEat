package com.isw.delivereat.ui.cart


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.isw.delivereat.R
import com.isw.delivereat.databinding.FragmentCartBinding
import com.isw.delivereat.dto.Producto
import com.isw.delivereat.ui.payment.PaymentActivity
import com.isw.delivereat.util.EventObserver
import kotlinx.android.synthetic.main.cart_activity.*
import kotlinx.android.synthetic.main.cart_activity.toolbar
import kotlinx.android.synthetic.main.fragment_cart.*

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Suppress("KDocMissingDocumentation")
class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val cartFragmentBinding : FragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        val rootView : View = cartFragmentBinding.root

        viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        viewModel.setUp()

        cartFragmentBinding.cartViewModel = viewModel

        viewModel.producto.observe(this, Observer<Producto> {
            cartFragmentBinding.producto = it
        })
        viewModel.onContinueBuying.observe(this, EventObserver {
            val intent = Intent(activity, PaymentActivity::class.java)
            intent.putExtra("producto", viewModel.producto.value)
            startActivity(intent)
        })

        continue_buying_button.setOnClickListener {
            viewModel.onContinueBuyingClicked(activity!!.toolbar)
        }

        plusButton.setOnClickListener {
            viewModel.onPlusProductoClicked()
        }

        minusButton.setOnClickListener {
            viewModel.onMinusProductoCliked()
        }

        return rootView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment CartFragment.
         */
        @JvmStatic
        fun newInstance() =
            CartFragment().apply {  }
    }
}
