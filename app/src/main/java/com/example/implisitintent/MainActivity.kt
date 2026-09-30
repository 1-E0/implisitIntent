package com.example.implisitintent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnKirimPesan = findViewById<Button>(R.id.btnKirimPesan)
        btnKirimPesan.setOnClickListener {
            val _sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra("address", "0811234")
                putExtra("sms_body", "ISI SMS")
                type = "text/plain"
            }

            if (_sendIntent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(_sendIntent, "PILIH APLIKASI "))
            }
        }

        val _alarmIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Coba Alarm")
            putExtra(AlarmClock.EXTRA_HOUR, 20)
            putExtra(AlarmClock.EXTRA_MINUTES, 10)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        startActivity(_alarmIntent)

        val _timerIntent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Coba Alarm")
            putExtra(AlarmClock.EXTRA_LENGTH, 20)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        startActivity(_timerIntent)

        val btnOpenURL = findViewById<Button>(R.id.btnOpenURL)
        val _etURL = findViewById<EditText>(R.id.etURL)
        btnOpenURL.setOnClickListener {
            val _webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://" + _etURL.text.toString())
            )
            if (_webIntent.resolveActivity(packageManager) != null ||
                packageManager.queryIntentActivities(_webIntent, 0).isNotEmpty()
            ) {
                startActivity(_webIntent)
            } else {
                Toast.makeText(this, "tidak ada aplikasi browser ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

        val btnSetEvent = findViewById<Button>(R.id.btnSetEvent)
        btnSetEvent.setOnClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                    val selectedDateTime = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)
                    }
                    val endTime = selectedDateTime.clone() as Calendar
                    endTime.add(Calendar.HOUR_OF_DAY, 1)

                    val eventIntent = Intent(Intent.ACTION_INSERT).apply {
                        data = CalendarContract.Events.CONTENT_URI
                        putExtra(CalendarContract.Events.TITLE, "Meeting")
                        putExtra(CalendarContract.Events.EVENT_LOCATION, "Kantor")
                        putExtra(CalendarContract.Events.DESCRIPTION, "Deskripsi Meeting")
                        putExtra(CalendarContract.Events.ALL_DAY, false)
                        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, selectedDateTime.timeInMillis)
                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                    }
                    startActivity(eventIntent)
                }, hour, minute, true)
                timePickerDialog.show()
            }, year, month, day)
            datePickerDialog.show()
        }

        val _ivHasil = findViewById<ImageView>(R.id.ivHasil)
        val _btnGetPhoto = findViewById<Button>(R.id.btnGetPhoto)

        val cameraLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            if (bitmap != null) {
                _ivHasil.setImageBitmap(bitmap)
            }
        }

        _btnGetPhoto.setOnClickListener {
            cameraLauncher.launch(null)
        }
    }
}