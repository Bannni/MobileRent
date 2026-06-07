package com.example.rentalapkgamesyaban.repository

import com.example.rentalapkgamesyaban.data.dao.UserDao
import com.example.rentalapkgamesyaban.data.entity.UserEntity

class UserRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    suspend fun register(user: UserEntity): Boolean {
        val existingUser = userDao.getUserByEmail(user.email)
        if (existingUser != null) {
            return false // User already exists
        }
        userDao.insertUser(user)
        return true
    }
}
