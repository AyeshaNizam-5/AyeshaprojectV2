package com.ayeshanizam.ayeshaprojectv2

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ayeshanizam.ayeshaprojectv2.R
import com.ayeshanizam.ayeshaprojectv2.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayeshanizam.ayeshaprojectv2.adapter.CustomRecyclerViewAdapter
import com.ayeshanizam.ayeshaprojectv2.songsDB.SongTrack
import com.ayeshanizam.ayeshaprojectv2.songsDB.SongTrackEntity
import com.ayeshanizam.ayeshaprojectv2.songsDB.SongViewModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity(),CustomRecyclerViewAdapter.ICustomInterface {

    lateinit var binding: ActivityMainBinding;
    lateinit var sharedPreferences: SharedPreferences
    lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var songViewModel: SongViewModel
    private val apiKey = "44adbaa4c180d850a5482aedc93f0a7b"
    private lateinit var mediaPlayer: MediaPlayer
    var songlist: ArrayList<SongTrackEntity> = ArrayList()
    lateinit var songUrl:String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        songViewModel = ViewModelProvider(this).get(SongViewModel::class.java)
        adapter = CustomRecyclerViewAdapter(this)
        binding.searchedSongsRecycler.setLayoutManager(LinearLayoutManager(this))
        binding.searchedSongsRecycler.adapter = adapter

        binding.topsearchBtn.setOnClickListener {
            songlist.clear()
            val query =  binding.etSearchBar.text.toString()
            if (query.isNotEmpty()) {
                searchTracks(query)
            }

        }

        // this is for accessing the bottom navigation bar and changing pages
        binding.bottomNavigation.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.settingsNavbtn -> {
                        val intent = Intent(this, SettingPage::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.favouritesNavbtn -> {
                        val intent = Intent(this, ExistingSongsActivity::class.java)
                        startActivity(intent)
                        finish()
                        true
                    }

                    else -> {
                        true
                    }
                }
            } catch (e: Exception) {
                throw e
            }
        }


    }
//--function used to search songs using retrofit and add them to songlist Arraylist----------------------------------------------------
    private fun searchTracks(query: String) {
        val service = ApiClient.instance.create(ApiService::class.java)
        val call = service.searchTracks(track = query, apiKey = apiKey)

        call.enqueue(object : Callback<LastFmResponse> {
            override fun onResponse(call: Call<LastFmResponse>, response: Response<LastFmResponse>) {
                if (response.isSuccessful) {
                    val tracks = response.body()?.results?.trackmatches?.track ?: emptyList()
                    tracks.forEach { track ->
                        songlist.add(SongTrackEntity(track.name,track.artist,track.url,track.listeners))
//                        Toast.makeText(this@MainActivity, "Track: ${track.name}," +
//                                " Artist: ${track.artist} URL: ${track.url}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MainActivity", "API call failed: $errorBody")
                    Toast.makeText(this@MainActivity, "Failed to get response: $errorBody", Toast.LENGTH_LONG).show()
                }

                adapter.setData(songlist)

            }
            override fun onFailure(call: Call<LastFmResponse>, t: Throwable) {
                Log.e("MainActivity", "API call failed", t)
                Toast.makeText(this@MainActivity, "API call failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun itemSelectedWithLongClick(item: SongTrackEntity) {
        songViewModel.addSong(item)
        Toast.makeText(this@MainActivity, item.toString(), Toast.LENGTH_LONG).show()
    }
//------------------------------------------------------

}
