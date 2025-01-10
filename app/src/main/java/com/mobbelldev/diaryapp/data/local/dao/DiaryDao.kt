package com.mobbelldev.diaryapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mobbelldev.diaryapp.data.local.entity.DiaryEntity

@Dao
interface DiaryDao {
    @Insert
    suspend fun insertDiary(diaryEntity: DiaryEntity)

    @Query("SELECT * FROM diary_table")
    fun getAllDiaries(): List<DiaryEntity>

    @Update
    suspend fun update(diaryEntity: DiaryEntity)

    @Delete
    suspend fun deleteDiary(diaryEntity: DiaryEntity)
}