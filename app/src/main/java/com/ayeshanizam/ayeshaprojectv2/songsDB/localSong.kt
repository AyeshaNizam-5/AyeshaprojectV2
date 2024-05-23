package com.ayeshanizam.ayeshaprojectv2.songsDB

class localSong (var name: String, var artist: String, var songlink: Int ){
    override fun toString(): String {
        return "selected song is:\n name='$name', artist='$artist'"
    }
}