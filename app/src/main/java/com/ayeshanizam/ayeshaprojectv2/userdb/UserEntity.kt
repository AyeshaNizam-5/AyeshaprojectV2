package com.ayeshanizam.ayeshaprojectv2.userdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "User")
class UserEntity(
    @PrimaryKey
    var userName: String,
    var password: String
) {
    override fun toString(): String {
        return "UserEntity(userName='$userName', password='$password')"
    }
}