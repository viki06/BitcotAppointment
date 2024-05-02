package com.example.bitcotappointment.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bitcotappointment.model.DBModels

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): DBModels.User?

    @Query("SELECT * FROM users WHERE type = :userType")
    suspend fun getUsersByType(userType: DBModels.UserType): List<DBModels.User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(it: DBModels.User)

}