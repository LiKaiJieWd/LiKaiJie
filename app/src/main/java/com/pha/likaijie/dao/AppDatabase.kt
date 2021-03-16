package com.pha.likaijie.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pha.likaijie.MyApplication
import com.pha.likaijie.bean.LKJ

/**
 *Create by LiKaiJie
 *Date:2020/12/5
 */
@Database(entities = [LKJ::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lkjDao(): LKJDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "user_db"
            ).fallbackToDestructiveMigration().build().apply {
                instance = this
            }
        }
    }



}