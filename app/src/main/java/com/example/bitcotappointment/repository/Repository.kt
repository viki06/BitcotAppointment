package com.example.bitcotappointment.repository

import com.example.bitcotappointment.model.DBModels
import com.example.bitcotappointment.room.DbController
import javax.inject.Inject


class Repository @Inject constructor(private val dbController: DbController) {

    suspend fun login(email: String, password: String): DBModels.User? {
        return dbController.login(email, password)
    }

    suspend fun getUsersByType(userType: DBModels.UserType): List<DBModels.User> {
        return dbController.getUsersByType(userType)
    }

    suspend fun getAppointmentsForDoctor(doctorId: Int): List<DBModels.AppointmentWithPatientDetails> =
        dbController.getAppointmentsForDoctor(doctorId)

    suspend fun insertAppointment(appointment: DBModels.Appointment) =
        dbController.insertAppointment(appointment)

    suspend fun updateAppointment(appointment: DBModels.Appointment) =
        dbController.updateAppointment(appointment)

    fun initDatabase() =
        dbController.initDatabase()

}
