package com.ayeshanizam.ayeshaprojectv2.songsDB

class SongTrack(var title: String, var artist: String, var url: String,  var listeners: Int)  {

    override fun toString(): String {
        return "SongTrack(title='$title', artist='$artist', url='$url', listeners=$listeners)"
    }
}