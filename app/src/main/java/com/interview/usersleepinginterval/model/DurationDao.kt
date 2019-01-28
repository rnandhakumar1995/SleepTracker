package com.interview.usersleepinginterval.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DurationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(durationEntity: DurationEntity)

    @Query("SELECT * FROM duration_table ORDER BY fromTime DESC")
    fun getAll(): LiveData<List<DurationEntity>>

    @Query("DELETE FROM duration_table WHERE toTime<:limit")
    fun deletePrev(limit: Long)
}