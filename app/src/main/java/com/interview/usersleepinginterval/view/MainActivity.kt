package com.interview.usersleepinginterval.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.interview.usersleepinginterval.R
import com.interview.usersleepinginterval.SleepTrackerService
import com.interview.usersleepinginterval.model.DurationDB
import com.interview.usersleepinginterval.model.DurationEntity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        if (isOptimizeActivityStarts()) {
            return
        }
        startService(Intent(this@MainActivity, SleepTrackerService::class.java))
        val durationDao = DurationDB.getInstance(this).getDurationDao()
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, -3)
        durationDao.deletePrev(calendar.timeInMillis)
        val allDuration = durationDao.getAll()
        var sleepingDurations = ArrayList<DurationEntity>()
        allDuration.observe(this, Observer {
            for (i in 0 until it.size - 1) {
                val isSleeping = (it[i].fromTime - it[i + 1].toTime) / (1000 /** 60 * 60*/) > 2
                if (isSleeping) {
                    sleepingDurations.add(DurationEntity(it[i].fromTime, it[i + 1].toTime))
                }
            }
            sleeping_time_rv.layoutManager = LinearLayoutManager(this)
            sleeping_time_rv.adapter = SleeperAdapter(sleepingDurations)
        })
    }

    private fun isBatteryOptimized(): Boolean {
        val pwrm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        val name = applicationContext.packageName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return !pwrm.isIgnoringBatteryOptimizations(name)
        }
        return false
    }

    private fun isOptimizeActivityStarts(): Boolean {
        if (isBatteryOptimized() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val name = resources.getString(R.string.app_name)
            Toast.makeText(
                applicationContext,
                "Battery optimization -> All apps -> $name -> Don't optimize",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            startActivity(intent)
            return true
        }
        return false
    }
}
