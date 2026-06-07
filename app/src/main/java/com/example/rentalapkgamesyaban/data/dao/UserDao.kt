package com.example.rentalapkgamesyaban.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rentalapkgamesyaban.data.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert
    suspend fun insertUser(user: UserEntity)
}
