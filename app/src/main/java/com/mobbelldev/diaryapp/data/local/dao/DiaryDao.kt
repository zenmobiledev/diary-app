package com.mobbelldev.diaryapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mobbelldev.diaryapp.data.local.entity.DiaryEntity

@Dao
interface DiaryDao {
    @Insert
    suspend fun insertDiary(diaryEntity: DiaryEntity)
}