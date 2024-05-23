package com.ayeshanizam.ayeshaprojectv2

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayeshanizam.ayeshaprojectv2.adapter.localSongsAdapter
import com.ayeshanizam.ayeshaprojectv2.databinding.ActivityExistingSongsBinding
import com.ayeshanizam.ayeshaprojectv2.songsDB.localSong

class ExistingSongsActivity : AppCompatActivity(), localSongsAdapter.ICustomInterface {
    private lateinit var binding: ActivityExistingSongsBinding
    private lateinit var adapter: localSongsAdapter
    private var existingSongList: ArrayList<localSong> = ArrayList()
    private var mediaPlayer: MediaPlayer? = null
    private var currentPlayingPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExistingSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareData()

        adapter = localSongsAdapter(this)
        binding.localSongsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.localSongsRecyclerView.adapter = adapter
        adapter.setData(existingSongList)

        binding.bottomNavigation.setOnItemSelectedListener {
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
                else -> true
            }
        }
    }

    private fun prepareData() {
        existingSongList = arrayListOf(
            localSong("Demons", "Imagine Dragons", R.raw.demons),
            localSong("Counting Stars", "One Republic", R.raw.countingstars),
            localSong("My Demons", "Starset", R.raw.mydemons),
            localSong("Pehli Dafa", "Atif Aslam", R.raw.pehlidafa),
            localSong("Blank Space", "Taylor Swift", R.raw.blankspace),
            localSong("Castle on the Hill", "Ed Sheeran", R.raw.castleonthehill),
            localSong("I Ain't Worried", "One Republic", R.raw.iaintworried)
        )
    }

    override fun itemSelectedWithLongClick(item: localSong) {
        Toast.makeText(this, "You have long clicked an item in the recycler view", Toast.LENGTH_LONG).show()
    }

    override fun onPlayPauseClick(item: localSong, position: Int, playPauseButton: ImageButton) {
        if (mediaPlayer?.isPlaying == true && currentPlayingPosition == position) {
            mediaPlayer?.pause()
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
        } else {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, item.songlink)
            mediaPlayer?.start()
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
            currentPlayingPosition = position
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}