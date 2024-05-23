package com.ayeshanizam.ayeshaprojectv2

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayeshanizam.ayeshaprojectv2.adapter.localSongsAdapter
import com.ayeshanizam.ayeshaprojectv2.databinding.ActivityExistingSongsBinding
import com.ayeshanizam.ayeshaprojectv2.songsDB.localSong
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat

class ExistingSongsActivity : AppCompatActivity(), localSongsAdapter.ICustomInterface {
    private lateinit var binding: ActivityExistingSongsBinding
    private lateinit var adapter: localSongsAdapter
    private var existingSongList: ArrayList<localSong> = ArrayList()
    private var mediaPlayer: MediaPlayer? = null
    private var currentPlayingPosition: Int = -1

    lateinit var bottomFragment : BottomFragment
    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction

    var gDetector: GestureDetectorCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExistingSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareData()

        adapter = localSongsAdapter(this)
        binding.localSongsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.localSongsRecyclerView.adapter = adapter
        adapter.setData(existingSongList)

        gDetector =  GestureDetectorCompat(this, CustomGesture())
        gDetector?.setOnDoubleTapListener(CustomGesture())


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class CustomGesture: GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {

        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val intent = Intent(this@ExistingSongsActivity, MainActivity::class.java)
            startActivity(intent)
            return false
        }
        override fun onDoubleTapEvent(e: MotionEvent): Boolean {

            return true
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