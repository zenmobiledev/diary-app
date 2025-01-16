package com.mobbelldev.diaryapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_table")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val title: String,
    val description: String,
)
