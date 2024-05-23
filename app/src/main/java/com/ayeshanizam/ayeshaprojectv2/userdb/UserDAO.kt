package com.ayeshanizam.ayeshaprojectv2.userdb

import androidx.room.*

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserEntity)

    @Query("SELECT * FROM User WHERE userName = :userName")
    fun getUser(userName: String): UserEntity?

    @Delete
    fun deleteUser(user: UserEntity): Int

    @Update
    fun updateUser(user: UserEntity): Int

    @Query("SELECT * FROM User WHERE userName LIKE :searchkey")
    fun searchUser(searchkey: String): List<UserEntity>
}
