package com.example.implisitintent

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

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
    }
}
