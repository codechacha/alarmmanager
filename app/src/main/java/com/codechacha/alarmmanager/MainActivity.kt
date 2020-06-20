package com.codechacha.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
                    this, AlarmReceiver.NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        onetimeAlarmToggle.setOnCheckedChangeListener(OnCheckedChangeListener { _, isChecked ->
            val toastMessage = if (isChecked) {
                val triggerTime = (SystemClock.elapsedRealtime()
                        + 60 * 1000)
                alarmManager.set(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime,
                        pendingIntent
                )
                "Onetime Alarm On"
            } else {
                alarmManager.cancel(pendingIntent)
                "Onetime Alarm Off"
            }
            Log.d(TAG, toastMessage)
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        })

        periodicAlarmToggle.setOnCheckedChangeListener(OnCheckedChangeListener { _, isChecked ->
            val toastMessage: String = if (isChecked) {
                val repeatInterval: Long = AlarmManager.INTERVAL_FIFTEEN_MINUTES
                val triggerTime = (SystemClock.elapsedRealtime()
                        + repeatInterval)
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime, repeatInterval,
                        pendingIntent)
                "Periodic Alarm On"
            } else {
                alarmManager.cancel(pendingIntent)
                "Periodic Alarm Off"
            }
            Log.d(TAG, toastMessage)
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        })

        exactPeriodicAlarmToggle.setOnCheckedChangeListener(OnCheckedChangeListener { _, isChecked ->
            val toastMessage: String = if (isChecked) {
                val repeatInterval: Long = 60*1000
                val triggerTime = (SystemClock.elapsedRealtime()
                        + repeatInterval)
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime, repeatInterval,
                        pendingIntent)
                "Exact periodic Alarm On"
            } else {
                alarmManager.cancel(pendingIntent)
                "Exact periodic Alarm Off"
            }
            Log.d(TAG, toastMessage)
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        })

        realtimePeriodicAlarmToggle.setOnCheckedChangeListener(OnCheckedChangeListener { _, isChecked ->
            val toastMessage: String = if (isChecked) {
                val repeatInterval: Long = 15 * 60 * 1000 // 15min
                val calendar: Calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 20)
                    set(Calendar.MINUTE, 25)
                }

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        repeatInterval,
                        pendingIntent)
                "Realtime periodic Alarm On"
            } else {
                alarmManager.cancel(pendingIntent)
                "Realtime periodic Alarm Off"
            }
            Log.d(TAG, toastMessage)
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        })

    }

}