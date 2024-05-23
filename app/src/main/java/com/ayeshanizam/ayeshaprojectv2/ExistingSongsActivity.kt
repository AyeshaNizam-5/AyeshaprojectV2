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
     var mediaPlayer: MediaPlayer? = null
    lateinit var adapter: localSongsAdapter
    var existingSonglist: ArrayList<localSong> = ArrayList()
    var selectedSong : localSong? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExistingSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preparedata()

        adapter = localSongsAdapter(this)
        binding.localSongsRecyclerView.setLayoutManager(LinearLayoutManager(this))
        binding.localSongsRecyclerView.adapter = adapter
        adapter.setData(existingSonglist)



        binding.fabPlay.setOnClickListener {
            if(selectedSong == null){
                Toast.makeText(this,"you have to select a song first to play", Toast.LENGTH_LONG).show()
            }
            else{
                mediaPlayer = MediaPlayer.create(this, selectedSong!!.songlink);
                mediaPlayer?.start()
            }

        }
        binding.fabStop.setOnClickListener {
            if (mediaPlayer !== null && mediaPlayer!!.isPlaying() ) {
                mediaPlayer!!.stop();
                mediaPlayer!!.release();
            }
        }

        binding.fabPause.setOnClickListener{
            var pausePosition: Int = 0
            if( mediaPlayer != null && mediaPlayer!!.isPlaying()){
                mediaPlayer!!.pause()
                pausePosition = mediaPlayer!!.currentPosition
            }
            else {
                mediaPlayer?.seekTo(pausePosition);
                mediaPlayer?.start();
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
            localSong("Demons","Imagine Dragons",R.raw.demons),
            localSong("Counting Stars","One Republic",R.raw.countingstars),
            localSong("My Demons","Starset",R.raw.mydemons),
            localSong("Pehli Dafa","Atif Aslam",R.raw.pehlidafa),
            localSong("Blank Space","Taylor Swift",R.raw.blankspace),
            localSong("Castle on the Hill","Ed Sheeran",R.raw.castleonthehill),
            localSong("Blank Space","One Republic",R.raw.iaintworried)
        )
    }

    override fun itemSelectedWithLongClick(item: localSong) {
        selectedSong = item
        binding.selectedSongTV.text = item.toString()
        Toast.makeText(this,"you have long clicked an item in recycler view", Toast.LENGTH_LONG).show()
    }

}