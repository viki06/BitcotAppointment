package com.example.bitcotappointment.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bitcotappointment.model.DBModels

@Dao
interface AppointmentDao {

    @Query(
        """
        SELECT appointments.*, users.*
        FROM appointments
        INNER JOIN users ON appointments.patientId = users.userId
        WHERE doctorId = :doctorId
    """
    )
    suspend fun getAppointmentsForDoctor(doctorId: Int): List<DBModels.AppointmentWithPatientDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: DBModels.Appointment)

    @Update
    suspend fun updateAppointment(appointment: DBModels.Appointment) : Int

}