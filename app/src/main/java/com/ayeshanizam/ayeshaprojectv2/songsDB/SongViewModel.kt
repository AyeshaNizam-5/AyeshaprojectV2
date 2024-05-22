package com.ayeshanizam.ayeshaprojectv2.songsDB

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*
it provides data to the UI and survive configuration changes. It acts as a communication center between repository and the UI
 */
class SongViewModel(application:Application):AndroidViewModel(application) {
    val readAllData: LiveData<List<SongTrackEntity>>
    lateinit var songDAO:SongDAO
    init {
        songDAO= SongRoomDatabase.getDatabase(application).songDao()
        readAllData = songDAO.getAllSongs()
    }
    fun addSong(song:SongTrackEntity){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            songDAO.insertSong(song)
        }
    }
    fun addSongs(customers: List<SongTrackEntity>){
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
            customers.forEach{
                songDAO.insertSong(it)
            }
        }
    }
    fun deleteSong(customer:SongTrackEntity){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            songDAO.deleteSong(customer)
        }
    }
    fun deleteAllSongs(){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            songDAO.deleteAllSongs()
        }
    }
    fun updateSong(customer:SongTrackEntity){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            songDAO.updateSong(customer)
        }
    }
    fun searchSong(searchkey:String):LiveData<List<SongTrackEntity>>{
            return songDAO.getSongsBySearchKey(searchkey).asLiveData()
    }

}