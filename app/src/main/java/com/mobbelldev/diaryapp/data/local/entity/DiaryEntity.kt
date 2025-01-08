package com.mobbelldev.diaryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val title: String,
    val description: String,
)
