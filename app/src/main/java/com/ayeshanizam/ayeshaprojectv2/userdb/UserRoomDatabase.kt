package com.ayeshanizam.ayeshaprojectv2.userdb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [com.ayeshanizam.ayeshaprojectv2.userdb.UserEntity::class], version = 1, exportSchema = false)

abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): com.ayeshanizam.ayeshaprojectv2.userdb.UserDAO
    companion object {
        private var INSTANCE: com.ayeshanizam.ayeshaprojectv2.userdb.UserRoomDatabase? = null
        fun getDatabase(context: android.content.Context): com.ayeshanizam.ayeshaprojectv2.userdb.UserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    com.ayeshanizam.ayeshaprojectv2.userdb.UserRoomDatabase::class.java,
                    "user_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}