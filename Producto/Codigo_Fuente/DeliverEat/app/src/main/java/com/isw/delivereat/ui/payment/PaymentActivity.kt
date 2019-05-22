package com.isw.delivereat.ui.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.isw.delivereat.R
import com.isw.delivereat.databinding.PaymentActivityBinding
import com.isw.delivereat.dto.TotalPagar
import com.isw.delivereat.ui.pay.PayActivity
import com.isw.delivereat.ui.shipment.ShipmentActivity
import com.isw.delivereat.util.EventObserver
import kotlinx.android.synthetic.main.payment_activity.*

/**
 * Created by Jeremias Gersicich on 2019-05-21
 */
class PaymentActivity : AppCompatActivity() {
    private lateinit var viewModel: PaymentViewModel
    private var FormaPago = null
    private var Direccion = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val paymentActivityBinding: PaymentActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.payment_activity)

        viewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)

        toolbar.title = "Mi Pedido"

        intent.extras?.let {
            viewModel.setProducto(it.getParcelable("producto"))
        }

        viewModel.producto.observe(this, Observer {
            paymentActivityBinding.producto = it
        })
        viewModel.subtotal.observe(this, Observer {
            paymentActivityBinding.subtotal = it
        })
        viewModel.onDireccionClicked.observe(this, EventObserver {
            val direccionIntent = Intent(this, DireccionActivity::class.java)
            if (paymentActivityBinding.direccion != null) {
                direccionIntent.putExtra("direccion", paymentActivityBinding.direccion)
            }
            startActivityForResult(direccionIntent, DIRECTION_ACTIVITY_CODE)
        })
        viewModel.formaPago.observe(this, Observer {
            paymentActivityBinding.formaPago = it
        })
        viewModel.direccion.observe(this, Observer {
            paymentActivityBinding.direccion = it
        })

        viewModel.onFormaPagoClicked.observe(this, EventObserver {
            val paymentIntent = Intent(this, PayActivity::class.java)
            val total = TotalPagar(65F + viewModel.subtotal.value!!)
            paymentIntent.putExtra("totalPagar", total)
            if (paymentActivityBinding.formaPago != null) {
                paymentIntent.putExtra("formaPago", paymentActivityBinding.formaPago)
            }
            startActivityForResult(paymentIntent, PAYMENT_ACTIVITY_CODE)
        })

        viewModel.onEnvioClicked.observe(this, EventObserver {
            val envioIntent = Intent(this, ShipmentActivity::class.java)
            startActivityForResult(envioIntent, SHIPMENT_ACTIVITY_CODE)
        })

        paymentActivityBinding.costoEnvio = 65F
        paymentActivityBinding.viewModel = viewModel

        continue_buying_button.setOnClickListener {
            validatePayment(paymentActivityBinding)
        }
    }

    private fun validatePayment(paymentActivityBinding: PaymentActivityBinding) {
        if (viewModel.formaPago.value == null || viewModel.direccion.value == null || viewModel.formaEnvio.value == null) {
            Toast.makeText(this, "Falta completar algunos datos", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Pedido confirmado!!!", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        val DIRECTION_ACTIVITY_CODE = 0x000001
        val PAYMENT_ACTIVITY_CODE = 0x000002
        val SHIPMENT_ACTIVITY_CODE = 0x000003

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DIRECTION_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.extras?.let {
                    viewModel.setDireccion(it.getParcelable("direccion"))
                }
            }
        } else if (requestCode == PAYMENT_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.extras?.let {
                    viewModel.setFormaPago(it.getParcelable("formaPago"))
                }
            }
        } else if (requestCode == SHIPMENT_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.extras?.let {
                    viewModel.setFormaEnvio(it.getParcelable("formaEnvio"))
                }
            }
        }
    }
}