package com.isw.delivereat.ui.payment

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isw.delivereat.dto.Direccion
import com.isw.delivereat.dto.FormaEnvio
import com.isw.delivereat.dto.FormaPago
import com.isw.delivereat.dto.Producto
import com.isw.delivereat.util.Event

/**
 * Created by Jeremias Gersicich on 2019-05-21
 */
class PaymentViewModel : ViewModel() {
    private val _producto = MutableLiveData<Producto>()
    private val _subtotal = MutableLiveData<Float>()
    private val _onDireccionClicked = MutableLiveData<Event<View>>()
    private val _onFormaPagoClicked = MutableLiveData<Event<View>>()
    private val _onEnvioClicked = MutableLiveData<Event<View>>()
    private val _direccion = MutableLiveData<Direccion>()
    private val _formaPago = MutableLiveData<FormaPago>()
    private val _formaEnvio = MutableLiveData<FormaEnvio>()

    val producto: LiveData<Producto>
        get() = _producto
    val subtotal: LiveData<Float>
        get() = _subtotal
    val onDireccionClicked: LiveData<Event<View>>
        get() = _onDireccionClicked
    val onFormaPagoClicked: LiveData<Event<View>>
        get() = _onFormaPagoClicked
    val onEnvioClicked: LiveData<Event<View>>
        get() = _onEnvioClicked
    val direccion: LiveData<Direccion>
        get() = _direccion
    val formaPago: LiveData<FormaPago>
        get() = _formaPago
    val formaEnvio: LiveData<FormaEnvio>
        get() = _formaEnvio

    fun setProducto(producto: Producto) {
        _producto.value = producto
        _subtotal.value = producto.precio * producto.cantidad

    }

    fun setDireccion(direccion: Direccion) {
        _direccion.value = direccion
    }

    fun setFormaPago(formaPago: FormaPago) {
        _formaPago.value = formaPago
    }

    fun setFormaEnvio(formaEnvio: FormaEnvio) {
        _formaEnvio.value = formaEnvio
    }

    fun directionClick(view: View) {
        _onDireccionClicked.value = Event(view)
    }

    fun formaPagoClick(view: View) {
        _onFormaPagoClicked.value = Event(view)
    }

    fun formaEnvioClick(view: View) {
        _onEnvioClicked.value = Event(view)
    }
}