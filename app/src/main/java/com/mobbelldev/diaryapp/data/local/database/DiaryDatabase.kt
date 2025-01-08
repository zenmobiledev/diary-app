package com.mobbelldev.diaryapp.data.local.database


//@Database(entities = [DiaryEntity::class], version = 1, exportSchema = false)
//abstract class DiaryDatabase : RoomDatabase() {
//    abstract fun diaryDao(): DiaryDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: DiaryDatabase? = null
//
//        fun getInstance(context: Context): DiaryDatabase {
////            return INSTANCE ?: synchronized(this) {
////                val instance = Room.databaseBuilder(context, DiaryDatabase::class.java, "diary_d")
////            }
//        }
//    }
//}