package com.ayeshanizam.ayeshaprojectv2.songsDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/*
If you change anything on the database like adding a field to table, changing type of a filed,
deleting a filed, changing the name of the field
exportSchema: to have a version of history of your schema in your code base,
it is not required so assigned as false
 */
@Database(
    entities = [SongTrackEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SongRoomDatabase : RoomDatabase() {
    abstract fun songDao(): SongDAO

    companion object{
        @Volatile  //it makes that instance to visible to other threads
        private var INSTANCE:SongRoomDatabase?=null

        fun getDatabase(context:Context):SongRoomDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return  tempInstance
            }
            /*
            everthing in this block protected from concurrent execution by multiple threads.In this block database instance is created
            same database instance will be used. If many instance are used, it will be so expensive
             */
            synchronized(this){
                val  instance =Room.databaseBuilder(context.applicationContext,
                    SongRoomDatabase::class.java, Constants.DATABASENAME).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
