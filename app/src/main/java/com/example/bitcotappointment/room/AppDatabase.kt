package com.example.bitcotappointment.room

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.bitcotappointment.model.DBModels

@Database(entities = [DBModels.User::class, DBModels.Appointment::class],  version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun appointmentDao(): AppointmentDao

}