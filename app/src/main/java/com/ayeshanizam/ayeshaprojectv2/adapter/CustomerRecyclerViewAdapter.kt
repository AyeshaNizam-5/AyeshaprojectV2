package com.ayeshanizam.ayeshaprojectv2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayeshanizam.ayeshaprojectv2.ApiServices.Artist
import com.ayeshanizam.ayeshaprojectv2.R
import com.ayeshanizam.ayeshaprojectv2.songsDB.SongTrackEntity
import com.ayeshanizam.ayeshaprojectv2.songsDB.localSong


class CustomRecyclerViewAdapter(private val context: Context):RecyclerView.Adapter<CustomRecyclerViewAdapter.RecyclerViewItemHolder>() {
    private var recyclerItemValues = emptyList<SongTrackEntity>()
    interface ICustomInterface{
        fun selectSearcheditem(item: SongTrackEntity)

    }
    lateinit var iCustomInterface:ICustomInterface

    init {
        iCustomInterface = context as ICustomInterface
    }
    fun setData(items: List<SongTrackEntity>){
        recyclerItemValues = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewItemHolder {
        val inflator = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflator.inflate(R.layout.recycler_item, viewGroup, false)
        return RecyclerViewItemHolder(itemView)
    }

    override fun onBindViewHolder(myRecyclerViewItemHolder: RecyclerViewItemHolder, position: Int) {
        val item = recyclerItemValues[position]
        myRecyclerViewItemHolder.songNameSearchTV.text = item.titleName
        myRecyclerViewItemHolder.artistNameSearchTV .text = item.artistName

        myRecyclerViewItemHolder.parentLayout.setOnClickListener {
            iCustomInterface.selectSearcheditem(item)
            true
        }
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    inner class RecyclerViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var parentLayout: LinearLayout
        lateinit var artistNameSearchTV: TextView
        lateinit var songNameSearchTV: TextView

        init {
            parentLayout = itemView.findViewById(R.id.itemRecycler)
            artistNameSearchTV = itemView.findViewById(R.id.artistNameRetrofitTV)
            songNameSearchTV = itemView.findViewById(R.id.songNameRetrofitTV)

        }
    }
}
