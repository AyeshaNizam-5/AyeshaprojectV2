package com.ayeshanizam.ayeshaprojectv2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayeshanizam.ayeshaprojectv2.adapter.localSongsAdapter
import com.ayeshanizam.ayeshaprojectv2.databinding.ActivityExistingSongsBinding
import com.ayeshanizam.ayeshaprojectv2.songsDB.localSong
import java.util.Collections

class ExistingSongsActivity : AppCompatActivity() , localSongsAdapter.ICustomInterface{
    lateinit var binding: ActivityExistingSongsBinding
    lateinit var mediaPlayer: MediaPlayer
    lateinit var adapter: localSongsAdapter
    var existingSonglist: ArrayList<localSong> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExistingSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preparedata()

        adapter = localSongsAdapter(this)
        binding.localSongsRecyclerView.setLayoutManager(LinearLayoutManager(this))
        binding.localSongsRecyclerView.adapter = adapter
        adapter.setData(existingSonglist)

        mediaPlayer = MediaPlayer.create(this, R.raw.countingstars);

        binding.fabPlay.setOnClickListener {
            mediaPlayer.start()
        }
        binding.fabStop.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(this, R.raw.countingstars);

            }
        }


// these following accesses the bottom navigation bar and changes from one page to another
        binding.bottomNavigation.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.settingsNavbtn -> {
                        val intent = Intent(this, SettingPage::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.homeNavbtn -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
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

    // creating list for the local songs that can be played.
    fun preparedata(){
        existingSonglist =ArrayList<localSong>()
        Collections.addAll(existingSonglist,
            localSong("Demons","Imagine Dragond",R.raw.demons),
            localSong("Counting Stars","One Republic",R.raw.countingstars),
            localSong("My Demons","Starset",R.raw.mydemons),
            localSong("Pehli Dafa","Atif Aslam",R.raw.pehlidafa),
            localSong("Blank Space","Taylor Swift",R.raw.blankspace),
            localSong("Castle on the Hill","Ed Sheeran",R.raw.castleonthehill),
            localSong("Blank Space","One Republic",R.raw.iaintworried)
        )
    }

    override fun itemSelectedWithLongClick(item: localSong) {
        Toast.makeText(this,"you have long clicked an item in recycler view", Toast.LENGTH_LONG).show()
    }

}