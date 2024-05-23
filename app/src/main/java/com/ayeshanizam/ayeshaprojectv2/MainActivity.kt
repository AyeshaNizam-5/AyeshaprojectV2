package com.ayeshanizam.ayeshaprojectv2

import ArtistsAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ayeshanizam.ayeshaprojectv2.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ayeshanizam.ayeshaprojectv2.adapter.CustomRecyclerViewAdapter
import com.ayeshanizam.ayeshaprojectv2.auth.LoginActivity
import com.ayeshanizam.ayeshaprojectv2.serviceImplement.CustomWorker
import com.ayeshanizam.ayeshaprojectv2.songsDB.SongTrackEntity
import com.ayeshanizam.ayeshaprojectv2.songsDB.SongViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ayeshanizam.ayeshaprojectv2.ApiServices.Artist
import com.ayeshanizam.ayeshaprojectv2.ApiServices.ArtistApiService
import com.ayeshanizam.ayeshaprojectv2.ApiServices.ArtistsResponse
import com.ayeshanizam.ayeshaprojectv2.ApiServices.artistApiClient
import kotlinx.coroutines.launch
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
    private lateinit var artistsAdapter: ArtistsAdapter
    private lateinit var topArtistsRecycler : RecyclerView


    lateinit var workManager: WorkManager

    lateinit var bottomFragment : BottomFragment
    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPrefCurrent = getSharedPreferences(com.ayeshanizam.ayeshaprojectv2.constants.Constants.SHARED_PREFS, Context.MODE_PRIVATE)
        val usernameCurrent = sharedPrefCurrent.getString(com.ayeshanizam.ayeshaprojectv2.constants.Constants.USERNAME_KEY, "")
        artistsAdapter = ArtistsAdapter(emptyList())
        topArtistsRecycler = binding.topArtistsRecycler
        topArtistsRecycler.layoutManager = LinearLayoutManager(this)
        topArtistsRecycler.adapter = artistsAdapter

        getTopArtists()
        //Service implementation#
        workManager = WorkManager.getInstance(this)
        binding.btnStartService.setOnClickListener {
            val username = usernameCurrent;
            val workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
                .setInputData(Data.Builder()
                    .putString("username", username)
                    .build())
                .build()

            workManager.enqueue(workRequest)
            Toast.makeText(this, "Work Request Enqueued", Toast.LENGTH_SHORT).show()

            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    Toast.makeText(this, "Work Succeeded! Notification launched", Toast.LENGTH_LONG).show()
                }
            })
        }

        //End of service implementation


        //check if username exists in shared preferences
        val sharedPref = getSharedPreferences(com.ayeshanizam.ayeshaprojectv2.constants.Constants.SHARED_PREFS, Context.MODE_PRIVATE)
        val username = sharedPref.getString(com.ayeshanizam.ayeshaprojectv2.constants.Constants.USERNAME_KEY, "")
        if (username.isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java )
            startActivity(intent)
            finish()
        }
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

    override fun selectSearcheditem(item: SongTrackEntity) {
        songViewModel.addSong(item)
        Toast.makeText(this@MainActivity, "Selected searched song has been added to db", Toast.LENGTH_LONG).show()
    }


    private fun getTopArtists() {
        val service = artistApiClient.apiService
        val call = service.getTopArtists(apiKey = apiKey)

        call.enqueue(object : Callback<ArtistsResponse> {
            override fun onResponse(call: Call<ArtistsResponse>, response: Response<ArtistsResponse>) {
                if (response.isSuccessful) {
                    val artists = response.body()?.artists?.artist ?: emptyList()
                    val artistList = artists.map { artist ->
                        Artist(artist.name, artist.playcount, artist.listeners, artist.mbid, artist.url, artist.streamable, artist.image)
                    }
                    Toast.makeText(this@MainActivity, "Top Artists Loaded ${artistList[0]}", Toast.LENGTH_LONG).show()
                    artistsAdapter.setData(artistList)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MainActivity", "API call failed: $errorBody")
                    Toast.makeText(this@MainActivity, "Failed to get response: $errorBody", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArtistsResponse>, t: Throwable) {
                Log.e("MainActivity", "API call failed", t)
                Toast.makeText(this@MainActivity, "API call failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
//------------------------------------------------------

}


