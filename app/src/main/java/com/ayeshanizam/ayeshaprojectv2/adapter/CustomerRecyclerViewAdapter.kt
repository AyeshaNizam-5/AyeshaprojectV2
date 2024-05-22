package com.ayeshanizam.ayeshaprojectv2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayeshanizam.ayeshaprojectv2.R
import com.ayeshanizam.ayeshaprojectv2.songsDB.SongTrackEntity


class CustomRecyclerViewAdapter(private val context: Context):RecyclerView.Adapter<CustomRecyclerViewAdapter.RecyclerViewItemHolder>() {
    private var recyclerItemValues = emptyList<SongTrackEntity>()
    interface ICustomInterface{
        fun itemSelectedWithLongClick(item:SongTrackEntity)
    }
    lateinit var iCustomInterface:ICustomInterface

    init {
        iCustomInterface = context as ICustomInterface
    }
    fun setData(items:List<SongTrackEntity>){
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
        myRecyclerViewItemHolder.songNameSearchTV.text = item.titleName
        myRecyclerViewItemHolder.artistNameSearchTV .text = item.artistName

        myRecyclerViewItemHolder.parentLayout.setOnLongClickListener {
            iCustomInterface.itemSelectedWithLongClick(item)
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
            parentLayout = itemView.findViewById(R.id.itemLayout)
            artistNameSearchTV = itemView.findViewById(R.id.artistNameSearchTV)
            songNameSearchTV = itemView.findViewById(R.id.songNameSearchTV)

        }
    }
}
