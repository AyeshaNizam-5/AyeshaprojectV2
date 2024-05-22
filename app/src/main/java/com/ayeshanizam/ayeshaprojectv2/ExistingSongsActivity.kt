package com.ayeshanizam.ayeshaprojectv2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayeshanizam.ayeshaprojectv2.databinding.ActivityExistingSongsBinding
import com.ayeshanizam.ayeshaprojectv2.songsDB.SongTrackEntity
import java.util.Collections

class ExistingSongsActivity : AppCompatActivity() {
    lateinit var binding: ActivityExistingSongsBinding
    private lateinit var mediaPlayer: MediaPlayer
    var existingSonglist: ArrayList<SongTrackEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExistingSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //mediaPlayer = MediaPlayer.create(this, R.raw);


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

    fun preparedata(){
        existingSonglist =ArrayList<SongTrackEntity>()
        //Collections.addAll(existingSonglist,
//            Customer(148, "veli", "korkmaz", 200.0),
//            Customer(897, "ali", "candan", 150.0),
//            Customer(333, "zeynep", "aydogmus", 100.0))
    }



}