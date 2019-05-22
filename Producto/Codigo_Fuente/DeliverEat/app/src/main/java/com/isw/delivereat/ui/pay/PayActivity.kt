package com.isw.delivereat.ui.pay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.isw.delivereat.R
import com.isw.delivereat.dto.FormaPago
import com.isw.delivereat.dto.Tarjeta
import com.isw.delivereat.dto.TotalPagar
import kotlinx.android.synthetic.main.pay_activity.*
import java.util.regex.Pattern


/**
 * Created by Jeremias Gersicich on 2019-05-21
 */
class PayActivity : AppCompatActivity() {
    private lateinit var totalPagar: TotalPagar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_activity)

        intent.extras?.let {
            totalPagar = it.getParcelable("totalPagar")!!
        }

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Seleccionar Forma de Pago"

        radiogroup.setOnCheckedChangeListener { radioGroup, i ->
            if (efectivoRadio.isChecked) {
                tarjetaContainer.visibility = View.GONE
                efectivoContainer.visibility = View.VISIBLE
            } else if (tarjetaRadio.isChecked) {
                tarjetaContainer.visibility = View.VISIBLE
                efectivoContainer.visibility = View.GONE
            }
        }

        guardar_button.setOnClickListener {
            if (validateInputs()) {
                var formaPago : FormaPago
                if (tarjetaRadio.isChecked) {
                    val tarjeta =
                        Tarjeta(
                            numeroEt.text.toString().trim(),
                            nombreEt.text.toString().trim(),
                            apellidoEt.text.toString().trim(),
                            cvcEt.text.toString().trim().toInt(),
                            fechaEt.text.toString().trim()
                            )

                    formaPago = FormaPago(false, totalPagar.monto, tarjeta)
                } else {
                    formaPago = FormaPago(true, montoEt.text.toString().trim().toFloat(), null)
                }

                val intent = Intent()
                intent.putExtra("formaPago", formaPago)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (efectivoRadio.isChecked) {
            if (montoEt.text.toString().isNullOrEmpty()) {
                montoIl.error = "Debe ingresar un monto"
                return false
            }
            val monto: Float = montoEt.text.toString().toFloat()
            if (monto < totalPagar.monto) {
                montoIl.error = "El monto debe ser mayor al total " + totalPagar.monto
                return false
            }
            montoIl.error = null
            return true
        } else if (tarjetaRadio.isChecked) {
            if (numeroEt.text.isNullOrEmpty()) {
                numeroIl.error = "Dato obligatorio"
                return false
            }

            var pattern = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?\$")
            var matcher = pattern.matcher(numeroEt.text)

            if (!matcher.matches()) {
                numeroIl.error = "No es una Tarjeta VISA válida"
                return false
            }

            numeroIl.error = null

            if (nombreEt.text.isNullOrEmpty()) {
                nombreIl.error = "Dato obligatorio"
                return false
            }

            nombreIl.error = null

            if (apellidoEt.text.isNullOrEmpty()) {
                apellidoEt.error = "Dato obligatorio"
                return false
            }

            apellidoIl.error = null

            pattern = Pattern.compile("^(0[1-9]|1[0-2])/?([0-9]{4}|[0-9]{2})\$")
            matcher = pattern.matcher(fechaEt.text)

            if (!matcher.matches()) {
                fechaIl.error = "Fecha de vencimiento no válida"
                return false
            }

            fechaIl.error = null

            pattern = Pattern.compile("^[0-9]{3,4}\$")
            matcher = pattern.matcher(cvcEt.text)

            if (!matcher.matches()) {
                cvcIl.error = "CVV no válido"
                return false
            }

            cvcIl.error = null

            return true
        }
        return true
    }
}