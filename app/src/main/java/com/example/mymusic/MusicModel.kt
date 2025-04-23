package com.example.musicapp

import java.io.Serializable

data class MusicModel(
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val path: String
) : Serializable