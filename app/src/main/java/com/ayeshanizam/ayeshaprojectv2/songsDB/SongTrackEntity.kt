package com.ayeshanizam.ayeshaprojectv2.songsDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "Song")
class SongTrackEntity(

    @PrimaryKey
    var titleName: String,
    var artistName: String,
    var url: String,
    var listeners:Int
    )
{
    override fun toString(): String {
        return "Detailed Song Information\n" +
                "titleName='$titleName', artistName='$artistName', url='$url', listeners=$listeners)"
    }
}