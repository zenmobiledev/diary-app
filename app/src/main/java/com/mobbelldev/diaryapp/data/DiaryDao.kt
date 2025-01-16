package com.mobbelldev.diaryapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiaryDao {
    @Insert
    suspend fun insertDiary(diaryEntity: DiaryEntity)

    @Query("SELECT * FROM diary_table ORDER BY id DESC")
    fun getAllDiaries(): List<DiaryEntity>

    @Update
    suspend fun update(diaryEntity: DiaryEntity)

    @Delete
    suspend fun deleteDiary(diaryEntity: DiaryEntity)

    // Search by title or date
    @Query("SELECT * FROM diary_table WHERE title LIKE :query OR date LIKE :query")
    suspend fun setSearchDiary(query: String): List<DiaryEntity>

    // Sorted by date in ascending order
    @Query("SELECT * FROM diary_table ORDER BY date ASC")
    fun getDiaryByAsc(): List<DiaryEntity>

    // Sorted by date in descending order
    @Query("SELECT * FROM diary_table ORDER BY date DESC")
    fun getDiaryByDesc(): List<DiaryEntity>

    // Get the latest diary
    @Query("SELECT * FROM diary_table ORDER BY id DESC LIMIT 1")
    fun getLatestDiaries(): DiaryEntity
}