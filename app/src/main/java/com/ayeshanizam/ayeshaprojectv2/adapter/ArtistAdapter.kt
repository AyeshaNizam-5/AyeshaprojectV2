import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayeshanizam.ayeshaprojectv2.ApiServices.Artist
import com.ayeshanizam.ayeshaprojectv2.R

class ArtistsAdapter(private var artists: List<Artist>) : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {

    inner class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val playcountTextView: TextView = itemView.findViewById(R.id.playcountTextView)
        val listenersTextView: TextView = itemView.findViewById(R.id.listenersTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artists[position]
        holder.nameTextView.text = artist.name
        holder.playcountTextView.text = "Playcount: ${artist.playcount}"
        holder.listenersTextView.text = "Listeners: ${artist.listeners}"
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    fun setData(newArtists: List<Artist>) {
        artists = newArtists
        notifyDataSetChanged()
    }
}
