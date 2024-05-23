package com.ayeshanizam.ayeshaprojectv2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayeshanizam.ayeshaprojectv2.R
import com.ayeshanizam.ayeshaprojectv2.songsDB.localSong

class localSongsAdapter(private val context: Context) : RecyclerView.Adapter<localSongsAdapter.RecyclerViewItemHolder>() {
    private var recyclerItemValues = emptyList<localSong>()
    private var playingPosition = RecyclerView.NO_POSITION

    interface ICustomInterface {
        fun itemSelectedWithLongClick(item: localSong)
        fun onPlayPauseClick(item: localSong, position: Int, playPauseButton: ImageButton)
    }

    lateinit var iCustomInterface: ICustomInterface

    init {
        iCustomInterface = context as ICustomInterface
    }

    fun setData(items: List<localSong>) {
        recyclerItemValues = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.item_layout, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(myRecyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val item = recyclerItemValues[position]
        myRecyclerViewItemHolder.songNameSearchTV.text = item.name
        myRecyclerViewItemHolder.artistNameSearchTV.text = item.artist

        // Update the play/pause button based on the current playing position
        if (playingPosition == position) {
            myRecyclerViewItemHolder.playPauseButton.setImageResource(android.R.drawable.ic_media_pause) // Set to pause icon
        } else {
            myRecyclerViewItemHolder.playPauseButton.setImageResource(android.R.drawable.ic_media_play) // Set to play icon
        }

        myRecyclerViewItemHolder.playPauseButton.setOnClickListener {
            val previousPlayingPosition = playingPosition
            if (playingPosition == position) {
                playingPosition = RecyclerView.NO_POSITION
            } else {
                playingPosition = position
            }
            notifyItemChanged(previousPlayingPosition)
            notifyItemChanged(playingPosition)
            iCustomInterface.onPlayPauseClick(item, position, myRecyclerViewItemHolder.playPauseButton)
        }

        myRecyclerViewItemHolder.parentLayout.setOnLongClickListener {
            iCustomInterface.itemSelectedWithLongClick(item)
            true
        }
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)
        var artistNameSearchTV: TextView = itemView.findViewById(R.id.artistNameSearchTV)
        var songNameSearchTV: TextView = itemView.findViewById(R.id.songNameSearchTV)
        var playPauseButton: ImageButton = itemView.findViewById(R.id.playPauseButton)
    }
}
