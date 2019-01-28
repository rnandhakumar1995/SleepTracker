package com.interview.usersleepinginterval.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "duration_table")
data class DurationEntity(@PrimaryKey var fromTime: Long, var toTime: Long)