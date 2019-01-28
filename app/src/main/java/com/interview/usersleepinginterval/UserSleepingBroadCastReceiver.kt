package com.interview.usersleepinginterval

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.interview.usersleepinginterval.model.DurationDB
import com.interview.usersleepinginterval.model.DurationEntity

class UserSleepingBroadCastReceiver : BroadcastReceiver() {
    private val TAG = UserSleepingBroadCastReceiver::class.java.simpleName

    companion object {
        var screenUnlockedTime: Long? = null
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            val currentTimeMillis = System.currentTimeMillis()
            if (intent.action == Intent.ACTION_USER_PRESENT) {
                screenUnlockedTime = currentTimeMillis
                Log.i(TAG, "Screen unlocked @ $screenUnlockedTime")
            } else if (intent.action == Intent.ACTION_SCREEN_OFF) {
                Log.i(TAG, "Screen locked!!!")
                if (screenUnlockedTime != null) {
                    DurationDB.getInstance(context).getDurationDao()
                        .insert(DurationEntity(screenUnlockedTime!!, currentTimeMillis))
                    screenUnlockedTime = null
                    Log.i(TAG, "Screen locked @ $currentTimeMillis")
                }
            }
        }
    }
}