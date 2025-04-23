package com.example.mymusic  // 👉 तुम्हारे package के हिसाब से हो सकता है बदलना पड़े

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.MusicModel
import com.example.musicapp.PlayerActivity

//import com.example.mymusic.PlayerActivity  // 👉 तुम्हारा PlayerActivity का सही path
//import com.example.mymusic.model.Music     // 👉 Music model का सही path

class MusicAdapter(
    private val context: Context,
    private val musicList: ArrayList<MusicModel>
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songTitle: TextView = itemView.findViewById(R.id.songTitle)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val song = musicList[position]

                    val intent = Intent(context, PlayerActivity::class.java)
                    intent.putExtra("song", song)
                    intent.putExtra("position", position)
                    intent.putExtra("songList", musicList)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val song = musicList[position]
        holder.songTitle.text = song.title
    }

    override fun getItemCount(): Int = musicList.size
}
