package com.isw.delivereat.ui.cart

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isw.delivereat.dto.Producto
import com.isw.delivereat.util.Event

/**
 * Created by Jeremias Gersicich on 2019-05-21
 */

class CartViewModel : ViewModel() {
    private val _producto = MutableLiveData<Producto>()
    private val _onContinueBuying = MutableLiveData<Event<View>>()

    val producto: LiveData<Producto>
        get() = _producto
    val onContinueBuying: LiveData<Event<View>>
        get() = _onContinueBuying

    fun getActivityTitle(): String {
        return ACTIVITY_TITLE
    }

    fun setUp() {
        _producto.value = Producto(
            "Hamburguesa con Queso",
            120F,
            1,
            "http://www.speedfood.cl/wp-content/uploads/2017/06/SIMPLE-QUESO.png"
        )
    }

    fun onContinueBuyingClicked(view: View) {
        _onContinueBuying.value = Event(view)
    }

    fun onPlusProductoClicked() {
        val producto = _producto.value!!
        producto.cantidad += 1

        _producto.value = producto
    }

    fun onMinusProductoCliked() {
        val producto = _producto.value!!

        if (producto.cantidad > 1) {
            producto.cantidad -= 1
            _producto.value = producto
        }
    }

    companion object {
        private val ACTIVITY_TITLE: String = "Mi Pedido"
    }
}