package com.interview.usersleepinginterval.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DurationEntity::class], version = 1)
abstract class DurationDB:RoomDatabase() {
    abstract fun getDurationDao(): DurationDao

    companion object {
        @Volatile
        private var instance: DurationDB? = null

        fun getInstance(context: Context): DurationDB {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DurationDB {
            return Room.databaseBuilder(context, DurationDB::class.java, "ImageDB").allowMainThreadQueries().build()
        }
    }
}