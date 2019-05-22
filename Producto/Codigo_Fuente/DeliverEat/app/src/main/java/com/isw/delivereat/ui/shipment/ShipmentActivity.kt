package com.isw.delivereat.ui.shipment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.isw.delivereat.R
import com.isw.delivereat.dto.FormaEnvio
import kotlinx.android.synthetic.main.shipment_activity.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Jeremias Gersicich on 2019-05-21
 */
class ShipmentActivity : AppCompatActivity() {
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    private var isValidDate = false

    val DATE_DIALOG_ID = 999
    val TIME_DIALOG_ID = 998

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.shipment_activity)

        fechaContainer.setOnClickListener {
            showDialog(DATE_DIALOG_ID)
        }

        timeContainer.setOnClickListener {
            showDialog(TIME_DIALOG_ID)
        }

        enviar_ahora.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                date_picker_container.visibility = View.GONE
            } else {
                date_picker_container.visibility = View.VISIBLE
            }
        }

        guardar_button.setOnClickListener {
            if (isValidDate || enviar_ahora.isChecked) {
                var formaEnvio = FormaEnvio(
                    enviar_ahora.isChecked(),
                    fechaEt.text.toString(),
                    timeEt.text.toString()
                )

                val intent = Intent()
                intent.putExtra("formaEnvio", formaEnvio)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        setCurrentDateOnView()
        setCurrentTimeOnView()
    }

    fun setCurrentTimeOnView() {
        val c = Calendar.getInstance()
        hour = c.get(Calendar.HOUR_OF_DAY)
        minute = c.get(Calendar.MINUTE)

        timeEt.setText(parseTime(hour, minute))
    }

    fun setCurrentDateOnView() {
        val c = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)

        // set current date into textview
        fechaEt.setText(
            StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" ")
        )
    }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            DATE_DIALOG_ID ->
                // set date picker as current date
                return DatePickerDialog(
                    this, datePickerListener,
                    year, month, day
                )
            TIME_DIALOG_ID ->
                return TimePickerDialog(
                    this,
                    timePickerListener,
                    hour, minute, false
                )
        }
        return null
    }

    private val timePickerListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

        val newTimeString = parseTime(hourOfDay, minute)
        val oldTimeString = parseTime(hour, minute)

        val sdf = SimpleDateFormat("HH:mm")

        val newTime = sdf.parse(newTimeString.toString())
        val oldTime = sdf.parse(oldTimeString.toString())

        if (newTime.before(oldTime)) {
            isValidDate = false
            timeIl.error = "La hora debe ser posterior a la actual"
        } else {
            isValidDate = true || isValidDate
            timeIl.error = null
            // set selected date into textview
            timeEt.setText(newTimeString)
        }
    }

    private fun parseTime(hourOfDay: Int, minute: Int): String {
        val stringBuilder = StringBuilder()

        if (hourOfDay < 10) {
            stringBuilder.append(0)
                .append(hourOfDay)
        } else {
            stringBuilder.append(hourOfDay)
        }

        stringBuilder.append(":")

        if (minute < 10) {
            stringBuilder.append(0)
                .append(minute)
        } else {
            stringBuilder.append(minute)
        }

        return stringBuilder.toString()
    }

    private val datePickerListener =
        DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            // when dialog box is closed, below method will be called.
            val newYear = selectedYear
            val newMonth = selectedMonth
            val newDay = selectedDay

            val oldDateString = StringBuilder().append(day)
                .append("-").append(month + 1).append("-").append(year)
                .append(" ")

            val newDateString = StringBuilder().append(newDay)
                .append("-").append(newMonth + 1).append("-").append(newYear)
                .append(" ")


            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val newDate = sdf.parse(newDateString.toString())
            val oldDate = sdf.parse(oldDateString.toString())

            if (newDate.before(oldDate)) {
                isValidDate = false
                fechaIl.error = "Debe ingresar una fecha posterior a la actual"
            } else {
                isValidDate = true || isValidDate
                fechaIl.error = null
                // set selected date into textview
                fechaEt.setText(newDateString)
            }
        }
}