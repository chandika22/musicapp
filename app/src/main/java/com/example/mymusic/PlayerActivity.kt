package com.example.musicapp

import android.media.MediaPlayer
import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mymusic.R

class PlayerActivity : AppCompatActivity() {

    private lateinit var songTitle: TextView
    private lateinit var btnPlayPause: Button
    private lateinit var btnNext: Button
    private lateinit var btnPrevious: Button
    private lateinit var seekBar: SeekBar

    private var mediaPlayer: MediaPlayer? = null
    private var currentSongIndex = 0
    private lateinit var songsList: ArrayList<MusicModel>

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        songTitle = findViewById(R.id.songTitle)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        btnNext = findViewById(R.id.btnNext)
        btnPrevious = findViewById(R.id.btnPrevious)
        seekBar = findViewById(R.id.seekBar)

        currentSongIndex = intent.getIntExtra("position", 0)
        songsList = intent.getSerializableExtra("songs") as ArrayList<MusicModel>

        playSong()

        btnPlayPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                btnPlayPause.text = "▶️"
            } else {
                mediaPlayer?.start()
                btnPlayPause.text = "⏸"
            }
        }

        btnNext.setOnClickListener {
            currentSongIndex = (currentSongIndex + 1) % songsList.size
            playSong()
        }

        btnPrevious.setOnClickListener {
            currentSongIndex = if (currentSongIndex - 1 < 0) songsList.size - 1 else currentSongIndex - 1
            playSong()
        }
    }
    private fun playSong() {
        mediaPlayer?.release()

        val song = songsList[currentSongIndex]
        songTitle.text = song.title

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(song.path)
                prepare()
                start()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error playing song!", Toast.LENGTH_SHORT).show()
            return
        }

        btnPlayPause.text = "⏸"
        seekBar.max = mediaPlayer?.duration ?: 0

        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    mediaPlayer?.let {
                        seekBar.progress = it.currentPosition
                        handler.postDelayed(this, 1000)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        handler.removeCallbacksAndMessages(null)
    }
}
