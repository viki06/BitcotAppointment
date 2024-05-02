package com.example.bitcotappointment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

object DBModels {

    @Entity(tableName = "users")
    data class User(
        @PrimaryKey(autoGenerate = true) val userId: Int = 0,
        val profileImage: String,
        val name: String,
        val age: Int,
        val gender: String,
        val type: UserType, // UserType enum: DOCTOR, PATIENT
        val email: String,
        val password: String
    )

    @Entity(tableName = "appointments",
        foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
    data class Appointment(
        @PrimaryKey(autoGenerate = true) val appointmentId: Int = 0,
        val doctorId: Int,
        val patientId: Int,
        var status: AppointmentStatus, // AppointmentStatus enum: PENDING, ACCEPTED, REJECTED, COMPLETED
        var comments: String
    )

    data class AppointmentWithPatientDetails(
        @Embedded val appointment: Appointment,
        @Embedded val patient: User
    )

    enum class UserType{
        DOCTOR,
        PATIENT
    }

    enum class AppointmentStatus(val string: String){
        PENDING("Pending"),
        ACCEPTED("Accepted"),
        REJECTED("Rejected"),
        COMPLETED("Completed")
    }

}