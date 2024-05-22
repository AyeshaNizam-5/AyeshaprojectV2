package com.ayeshanizam.ayeshaprojectv2.songsDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ayeshanizam.ayeshaprojectv2.songsDB.Constants
import kotlinx.coroutines.flow.Flow


//Data Access Object: It contains all the methods used for accessing to the database. Inside it all the required queries will be created
@Dao
interface SongDAO {
    // The conflict strategy defines what happens,if there is an existing entry.
    // The default action is ABORT.
    //@Insert(onConflict = OnConflictStrategy.IGNORE) //if there is a conflict it will be ignored, if there is a new customer with the same data it will jut ignored
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSong(song: SongTrackEntity) // suspend is written because it will be used with coroutine

    @Update
    fun updateSong(song: SongTrackEntity)

    @Delete
    fun deleteSong(song: SongTrackEntity)

    @Query("DELETE FROM ${Constants.TABLENAME}")
    fun deleteAllSongs()

    /*
    LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware,
    meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    This awareness ensures LiveData only updates app component observers that are in an active lifecycle state
    */
    @Query("SELECT * FROM ${Constants.TABLENAME} ORDER BY titleName ASC")
    fun getAllSongs():LiveData<List<SongTrackEntity>>

//    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE id =:id")
//    fun getSongById(id:Int):SongTrackEntity

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE titleName LIKE :searchKey")
    fun getSongsBySearchKey(searchKey:String): Flow<List<SongTrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSongs(customers: ArrayList<SongTrackEntity>){
        customers.forEach{
            insertSong(it)
        }
    }

}